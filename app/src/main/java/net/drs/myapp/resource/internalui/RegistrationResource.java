package net.drs.myapp.resource.internalui;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.drs.myapp.api.INotifyByEmail;
import net.drs.myapp.api.INotifyBySMS;
import net.drs.myapp.api.IPaymentService;
import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dto.CompleteRegistrationDTO;
import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.dto.SMSDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.Otp;
import net.drs.myapp.model.OtpDTO;
import net.drs.myapp.model.PaymentDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;
import net.drs.myapp.notification.NotificationDataConstants;
import net.drs.myapp.notification.NotificationRequest;
import net.drs.myapp.notification.NotificationTemplate;
import net.drs.myapp.notification.NotificationType;
import net.drs.myapp.resource.GenericService;
import net.drs.myapp.response.handler.ExeceptionHandler;
import net.drs.myapp.response.handler.SuccessMessageHandler;
import net.drs.myapp.utils.AppUtils;
import net.drs.myapp.utils.TransactionStatus;

@CrossOrigin
@RequestMapping("/guest")
@Controller
public class RegistrationResource extends GenericService {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationResource.class);

	@Autowired
	IRegistrationService registrationService;

	@Autowired
	INotifyByEmail notificationByEmailService;

	@Autowired
	INotifyBySMS notificationBySMSService;

	@Autowired
	IUserDetails userDetails;

	@Autowired
	IPaymentService paymentService;

	@Value("${notificationByEmail.or.SMS}")
	private String notifyByEmailOrSMS;

	@PostMapping(value = "/pgresponse")
	public String getResponseRedirect(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

		System.out.println("OOOKKKKKKKKKKKKKK" + request.getParameter("token"));
		Map<String, String[]> mapData = request.getParameterMap();
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		mapData.forEach((key, val) -> parameters.put(key, val[0]));
		String paytmChecksum = "";
		if (mapData.containsKey("CHECKSUMHASH")) {
			paytmChecksum = mapData.get("CHECKSUMHASH")[0];
		}
		String result;
		PaymentDTO payment = new PaymentDTO();
		boolean isValideChecksum = false;

		payment.setOrderId(parameters.get("ORDERID"));
		System.out.println("RESULT : " + parameters.toString());
		try {
			isValideChecksum = validateCheckSum(parameters, paytmChecksum);
			if (isValideChecksum && parameters.containsKey("RESPCODE")) {
				if (parameters.get("RESPCODE").equals("01")) {
					result = "Payment Successful";
					payment.setResponse(parameters.toString());
					payment.setTransactionStatus(TransactionStatus.SUCCESS.name());
					PaymentDTO paymentDTO = paymentService.updatePaymentDetails(payment);
					UserDTO userDTO = new UserDTO();
					userDTO.setUserId(paymentDTO.getMemberId());
					userDTO.setActive(true);
					userDTO.setPaymentStatus("SUCCESS");
					// activate user;
					userDetails.activeteUser(userDTO);
					redirectAttributes.addFlashAttribute("successMessage", "The Member is added Successfully");
					return "redirect:/user/addMember";
				} else {
					result = "Payment Failed";
					redirectAttributes.addFlashAttribute("errorMessage",
							"Payment was not Success. The Member is not added. Please try again");
					return "redirect:/user/addMember";
				}
			} else {
				result = "Checksum mismatched";
				redirectAttributes.addFlashAttribute("errorMessage",
						"Payment was not Success. The Member is not added. Please try again");
				return "redirect:/user/addMember";
			}
		} catch (Exception e) {
			result = e.toString();
			redirectAttributes.addFlashAttribute("errorMessage",
					"Payment was not Success. The Member is not added. Please try again");
			return "redirect:/user/addMember";
		}
	}

	// this is just for test case purpose. This should not be used by external
	// calls
	@PostMapping("/addAdmin")
	public ResponseEntity<?> addAdmin(@AuthenticationPrincipal Principal principal, @RequestBody UserDTO userDTO,
			BindingResult bindingResult) {
		java.util.Date uDate = new java.util.Date();
		userDTO.setDateOfCreation(new java.sql.Date(uDate.getTime()));
		userDTO.setLastUpdated(new java.sql.Date(uDate.getTime()));
		try {
			Set<Role> roles = new HashSet();
			Role role = new Role();
			role.setRole(net.drs.myapp.utils.Role.ADMIN.getRole());
			roles.add(role);
			Role role1 = new Role();
			role1.setRole(net.drs.myapp.utils.Role.USER.getRole());
			roles.add(role1);
			Role role2 = new Role();
			role2.setRole(net.drs.myapp.utils.Role.MATRIMONY.getRole());
			roles.add(role2);
			userDTO = registrationService.adduser(userDTO, roles);
			SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), "User Added Successfully", "");
			return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/verifyEmail")
	public ModelAndView verifyEmail(String emailId) {

		if (emailId == null || StringUtils.isEmpty(emailId)) {
			return new ModelAndView("registrationSuccess").addObject("notificationType", "EMAIL");
		}
		return new ModelAndView("registrationSuccess").addObject("userEmailId", emailId);
	}

	@PostMapping(value = "/resendOtp")
	public ModelAndView resendOtp(@RequestParam(defaultValue = "XXXXXXXXX") String phoneNumber) {
		ModelAndView modelandView = new ModelAndView();
		String message = "";
		try {
			Users user = registrationService.checkIfUserExists(phoneNumber, "SMS");

			if (user != null) {
				char[] otpdigits = AppUtils.fourDigitOTPForMobileVerification();
				String smsMessage = String.format("OTP for phone verification is %s", new String(otpdigits));
				System.out.println("SMS OTP SENT :" + smsMessage);
				Otp otp = new Otp();
				otp.setOtpSentTimeStamp(AppUtils.getCurrentTimeStamp());
				otp.setUniqueOTPSent(otpdigits);
				otp.setUserId(user.getId());
				otp.setOtpValidFor(1); // needs to be changed - 1 hour -
										// assumption
				otp.setIsvalidated(false);
				otp.setPhoneNumber(phoneNumber);
				notificationBySMSService.insertOTP(otp);

				SMSDTO smsDTO = new SMSDTO(user.getId(), user.getPhonenumber(), smsMessage);
				notificationBySMSService.sendPhoneNumberVerificationSMS(smsDTO);
				message = String.format("OTP is sent to the provided Phone Number: %s. ",
						phoneNumber + ".  Verify your phone number to activate your account ");

			} else {
				message = String.format("Provided Phone number doesnt exist. Please sign up  again ");
			}

			modelandView.addObject("notificationType", NotificationType.SMS.toString());
			modelandView.addObject("phoneNumber", phoneNumber);
			modelandView.addObject("message", message);
			modelandView.setViewName("registrationSuccess");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		return modelandView;
	}

	@PostMapping("/addUser")
	public ModelAndView addUser(UserDTO userDTO, BindingResult bindingResult, WebRequest request) {

		ModelAndView modelandView = new ModelAndView();
		String successMessage = "";
		java.util.Date uDate = new java.util.Date();
		Set<Role> roles = new HashSet<>();
		Long notificationId = 0L;
		userDTO.setDateOfCreation(new java.sql.Date(uDate.getTime()));
		userDTO.setLastUpdated(new java.sql.Date(uDate.getTime()));
		userDTO.setCreatedBy(ApplicationConstants.USER_GUEST);
		userDTO.setCreationDate(new java.sql.Date(uDate.getTime()));
		userDTO.setUpdatedBy(ApplicationConstants.USER_GUEST);
		userDTO.setUpdatedDate(new java.sql.Date(uDate.getTime()));

		try {
			NotificationRequest notificationReq = null;
			Map<String, String> data = new HashMap<String, String>();
			Role role = new Role();
			role.setRole(ApplicationConstants.ROLE_USER);
			roles.add(role);

			if (AppUtils.isEmailId(userDTO.getMobileNumberOrEmail())) {
				userDTO.setEmailAddress(userDTO.getMobileNumberOrEmail());
			} else if (AppUtils.isPhoneNumber(userDTO.getMobileNumberOrEmail())) {
				userDTO.setMobileNumber(userDTO.getMobileNumberOrEmail());
			} else {
				throw new Exception("Enter valid phone number or email id");
			}

			User user = registrationService.adduserandGetId(userDTO, roles);
			if (user != null && user.getUserId() > 0 && AppUtils.isEmailId(userDTO.getMobileNumberOrEmail())) {
				EmailDTO emailDto = new EmailDTO();
				emailDto.setEmailId(userDTO.getEmailAddress());
				emailDto.setCreatedBy(ApplicationConstants.USER_SYSTEM);
				emailDto.setCreationDate(new java.sql.Date(uDate.getTime()));
				emailDto.setUpdatedBy(ApplicationConstants.USER_SYSTEM);
				emailDto.setUpdatedDate(new java.sql.Date(uDate.getTime()));
				emailDto.setEmailTemplateId(NotificationTemplate.NEW_REGISTRATION.getNotificationType());
				emailDto.setUserID(user.getUserId());
				emailDto.setNeedtoSendEmail(true);
				notificationId = notificationByEmailService.insertDatatoDBforNotification(emailDto);
				data.put(NotificationDataConstants.USER_NAME, userDTO.getFirstName());
				data.put(NotificationDataConstants.TEMPERORY_ACTIVATION_STRING, user.getTemporaryActivationString());
				notificationReq = new NotificationRequest(notificationId, emailDto.getEmailId(), null, data,
						NotificationTemplate.NEW_REGISTRATION, NotificationType.EMAIL);
				// rabbitMqService.publishSMSMessage(notificationReq);

				notificationByEmailService.sendNotoficationDirectly(notificationReq);
				modelandView.addObject("notificationType", NotificationType.EMAIL.toString());
				successMessage = String.format(
						"An Email Sent to the provided Email id: %s. "
								+ "Activate your account by using code sent to your email ID",
						userDTO.getEmailAddress());
				SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), successMessage, "");

			} else if (user != null && user.getUserId() > 0
					&& AppUtils.isPhoneNumber((userDTO.getMobileNumberOrEmail()))) {

				char[] otpdigits = AppUtils.fourDigitOTPForMobileVerification();
				String smsMessage = String.format("OTP for phone verification is %s", new String(otpdigits));
				System.out.println("SMS OTP SENT :" + smsMessage);
				Otp otp = new Otp();
				otp.setOtpSentTimeStamp(AppUtils.getCurrentTimeStamp());
				otp.setUniqueOTPSent(otpdigits);
				otp.setUserId(user.getUserId());
				otp.setOtpValidFor(1); // needs to be changed - 1 hour -
										// assumption
				otp.setIsvalidated(false);
				otp.setPhoneNumber(userDTO.getMobileNumberOrEmail());
				notificationBySMSService.insertOTP(otp);

				SMSDTO smsDTO = new SMSDTO(user.getUserId(), user.getMobileNumber(), smsMessage);
				notificationBySMSService.sendPhoneNumberVerificationSMS(smsDTO);

				modelandView.addObject("notificationType", NotificationType.SMS.toString());
				modelandView.addObject("phoneNumber", userDTO.getMobileNumberOrEmail());
				successMessage = String.format("OTP is sent to the provided Phone Number: %s. ",
						userDTO.getMobileNumberOrEmail() + ".  Verify your phone number to activate your account ");
				SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), successMessage, "");
			}
			//
			modelandView.addObject("registrationSuccess", true);
			modelandView.addObject("message", successMessage);
			// modelandView.addObject("userEmailId", userDTO.getEmailAddress());
			modelandView.setViewName("registrationSuccess");
			return modelandView;
		} catch (Exception e) {
			e.printStackTrace();

			ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
			modelandView.addObject("message", e.getMessage());
			modelandView.addObject("registrationSuccess", false);
			modelandView.setViewName("loginFailure");
			return modelandView;
		}
	}

	@PostMapping("/verifyOTP")
	public ModelAndView verifyOTP(OtpDTO otpDTO) {
		String message = "";
		ModelAndView modelAndView = new ModelAndView();
		logger.debug("Verifying user " + otpDTO.getPhoneNumber());
		try {
			registrationService.verifyOtpForPhonumber(otpDTO);
			SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(),
					"Congratulations!. Your account is active. You can login.", "");
			return new ModelAndView("welcome").addObject("actionResult", true).addObject("message",
					"Congratulation!. Your account is active. You can login now");
		} catch (Exception e) {

			modelAndView.addObject("notificationType", NotificationType.SMS.toString());
			modelAndView.addObject("phoneNumber", otpDTO.getPhoneNumber());
			message = String.format("Entered OTP doesnt match. Enter right OTP.");
		}
		modelAndView.addObject("registrationSuccess", false);
		modelAndView.addObject("message", message);
		modelAndView.setViewName("registrationSuccess");
		return modelAndView;
	}

	@PostMapping("/activateUser")
	public ModelAndView activateAccount(UserDTO userDTO, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		try {

			if (!userDTO.getPassword().equalsIgnoreCase(userDTO.getConfirmPassword())) {
				throw new Exception(
						"Password and Confirm Password are not same. Please enter correct password and confirm password");
			}

			if (userDTO.getMobileNumberOrEmail() != null && AppUtils.isEmailId(userDTO.getMobileNumberOrEmail())) {
				System.out.println("Activating using Email id ");
				userDTO.setEmailAddress(userDTO.getMobileNumberOrEmail());
				registrationService.activateUserAccount(userDTO);
				redirectAttributes.addFlashAttribute("registrationSuccess", true);
				redirectAttributes.addFlashAttribute("message",
						"Congratulation!. Your account is active. You can login now");
			} else if (userDTO.getMobileNumberOrEmail() != null
					&& AppUtils.isPhoneNumber(userDTO.getMobileNumberOrEmail())) {
				System.out.println("Activating using Phone Number ");
				OtpDTO otp = new OtpDTO();
				otp.setPhoneNumber(userDTO.getMobileNumberOrEmail());
				otp.setOtpNumber(userDTO.getTemporaryActivationString()); // OTP
				otp.setPassword(userDTO.getPassword());
				return verifyOTP(otp);
			}

			return new ModelAndView("redirect:/guest/verifyEmail");

		} catch (Exception e) {
//			ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
//			return new ModelAndView("registrationSuccess").addObject("actionResult", false).addObject("message",
//					e.getMessage());
			redirectAttributes.addFlashAttribute("registrationSuccess", false);
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return new ModelAndView("redirect:/guest/verifyEmail");

		}
	}

	// for ajax call
	// @PostMapping("/forgotPassword")
	// public ModelAndView forgotPassword(String emailIdorPhoneNumber) {
	//
	// ModelAndView modelAndView = new ModelAndView();
	// try {
	//
	// if (org.springframework.util.StringUtils.isEmpty(emailIdorPhoneNumber)) {
	// throw new Exception("Please enter valid email id");
	// }
	//
	// boolean isPhoneNumber = AppUtils.isPhoneNumber(emailIdorPhoneNumber);
	// if(isPhoneNumber) {
	// // send OTP
	//
	// Users user= registrationService.checkIfUserExists(emailIdorPhoneNumber,
	// "SMS");
	//
	// if(user !=null) {
	// char[] otpdigits = AppUtils.fourDigitOTPForMobileVerification();
	// String smsMessage = String.format("OTP for phone verification is %s", new
	// String(otpdigits));
	// System.out.println("SMS OTP SENT :" + smsMessage );
	// Otp otp = new Otp();
	// otp.setOtpSentTimeStamp(AppUtils.getCurrentTimeStamp());
	// otp.setUniqueOTPSent(otpdigits);
	// otp.setUserId( user.getId() );
	// otp.setOtpValidFor(1); // needs to be changed - 1 hour - assumption
	// otp.setIsvalidated(false);
	// otp.setPhoneNumber(emailIdorPhoneNumber);
	// notificationBySMSService.insertOTP(otp);
	//
	// SMSDTO smsDTO = new SMSDTO(user.getId(), user.getPhonenumber(),
	// smsMessage );
	// notificationBySMSService .sendPhoneNumberVerificationSMS(smsDTO);
	// String message = String.format("OTP is sent to the provided Phone Number:
	// %s. ", emailIdorPhoneNumber + ". Verify your phone number to activate
	// your account ");
	// modelAndView.addObject("notificationType",
	// NotificationType.SMS.toString());
	// modelAndView.addObject("phoneNumber",emailIdorPhoneNumber);
	// modelAndView.addObject("message", message);
	// modelAndView.setViewName("registrationSuccess");
	//
	// }
	// }else {
	// // email
	// registrationService.forgotPassword(emailIdorPhoneNumber);
	// }
	// SuccessMessageHandler messageHandler = new SuccessMessageHandler(new
	// Date(), "Temperory Password has been sent to your Email id. Use it to
	// reset your password", "");
	//
	// } catch (Exception e) {
	// ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(),
	// e.getMessage(), "");
	//
	// }
	// return modelAndView;
	// }

	@PostMapping("/forgotPassword")
	public ResponseEntity<?> forgotPassword(String emailId) {
		try {
			if (org.springframework.util.StringUtils.isEmpty(emailId)) {
				throw new Exception("Please enter valid email id");
			}
			String message = registrationService.forgotPassword(emailId);
			SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), message, "");
			return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
		} catch (Exception e) {
			ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(UserDTO userDTO, BindingResult bindingResult) {

		try {

			java.util.Date uDate = new java.util.Date();
			if (StringUtils.isEmpty(userDTO.getMobileNumberOrEmail())) {
				throw new Exception("Entered Email / Phone number cannot be blank");
			}
			if (StringUtils.isEmpty(userDTO.getPassword())) {
				throw new Exception("Password Cannot be Blank.");
			}
			if (!userDTO.getPassword().equalsIgnoreCase(userDTO.getConfirmPassword())) {
				throw new Exception(
						"Password and Confirm Password doesnt match. Please try again with correct password");
			}
			registrationService.resetPassword(userDTO);
			SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(),
					"Password Resetted Successfully", "");
			return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/completeRegistration")
	public ResponseEntity<?> completeRegistration(@RequestBody CompleteRegistrationDTO completeRegistrationDTO,
			BindingResult bindingResult) {

		java.util.Date uDate = new java.util.Date();
		Set<Role> roles = new HashSet();
		completeRegistrationDTO.setCreatedDate(new java.sql.Date(uDate.getTime()));
		completeRegistrationDTO.setUpdatedDate(new java.sql.Date(uDate.getTime()));
		completeRegistrationDTO.setCreatedBy(ApplicationConstants.USER_SYSTEM);
		completeRegistrationDTO.setUpdatedBy(ApplicationConstants.USER_SYSTEM);
		// USER_SYSTEM means my user registration.

		try {
			Role role = new Role();
			role.setRole(ApplicationConstants.ROLE_USER);
			roles.add(role);
			boolean result = registrationService.completeRegistration(completeRegistrationDTO);
			if (result) {
				SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(),
						"User Details added Successfully", "");
				return new ResponseEntity<>(messageHandler, HttpStatus.ACCEPTED);
			} else {
				ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(),
						"Unable to store user details. Please try after some time...", "");
				return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
			return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/all")
	// @PreAuthorize("hasAnyRole('USER')")
	public String hello(@AuthenticationPrincipal Principal principal) {

		principal.getName();
		return "Hello Youtube";
	}

	// check SecurityConfig. An entry is there for /v1/guest/
	@GetMapping("/test")
	// @PreAuthorize("hasAnyRole('ROLE_USER')")
	public String test() {
		return "welcome";
	}

}

package net.drs.myapp.resource.internalui;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import net.drs.common.notifier.NotificationDataConstants;
import net.drs.common.notifier.NotificationRequest;
import net.drs.common.notifier.NotificationTemplate;
import net.drs.common.notifier.NotificationType;
import net.drs.myapp.api.INotifyByEmail;
import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dto.CompleteRegistrationDTO;
import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.dto.SMSDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.mqservice.RabbitMqService;
import net.drs.myapp.resource.GenericService;
import net.drs.myapp.response.handler.ExeceptionHandler;
import net.drs.myapp.response.handler.SuccessMessageHandler;
import net.drs.myapp.utils.AppUtils;

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
    RabbitMqService rabbitMqService;

    @Value("${notificationByEmail.or.SMS}")
    private String notifyByEmailOrSMS;

    // this is just for test case purpose. This should not be used by external
    // calls
    @PostMapping("/addAdmin")
    public ResponseEntity<?> addAdmin(@AuthenticationPrincipal Principal principal, @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        java.util.Date uDate = new java.util.Date();
        userDTO.setDateOfCreation(new java.sql.Date(uDate.getTime()));
        userDTO.setLastUpdated(new java.sql.Date(uDate.getTime()));
        try {
            Set<Role> roles = new HashSet();
            Role role = new Role();
            role.setRole(ApplicationConstants.ROLE_ADMIN);
            roles.add(role);
            Role role1 = new Role();
            role1.setRole(ApplicationConstants.ROLE_USER);
            roles.add(role1);
            userDTO = registrationService.adduser(userDTO, roles);
            SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), "User Added Successfully", "");
            return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addUser")
    public ModelAndView addUser(UserDTO userDTO, BindingResult bindingResult, WebRequest request) {

        ModelAndView modelandView = new ModelAndView();

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
            } else if (AppUtils.isEmailId(userDTO.getMobileNumberOrEmail())) {
                userDTO.setMobileNumber(userDTO.getMobileNumberOrEmail());
            } else {
                throw new Exception("Enter valid phone number or email id");
            }
            User user = registrationService.adduserandGetId(userDTO, roles);
            if (user != null && 
                user.getUserId() > 0 && 
                AppUtils.isEmailId(userDTO.getMobileNumberOrEmail())) {
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
                notificationReq = new NotificationRequest(notificationId, emailDto.getEmailId(), null, data, NotificationTemplate.NEW_REGISTRATION, NotificationType.EMAIL);
              //  rabbitMqService.publishSMSMessage(notificationReq);
            
                notificationByEmailService.sendNotoficationDirectly(notificationReq);
            
            } else if (user != null && user.getUserId() > 0 && notifyByEmailOrSMS.equalsIgnoreCase(NotificationType.SMS.getNotificationType())
                    && AppUtils.isPhoneNumber(userDTO.getMobileNumberOrEmail())) {
                SMSDTO smsDTO = new SMSDTO(user.getUserId(), user.getMobileNumber(), "otp message");
                smsDTO = notificationByEmailService.insertDatatoDBforNotification(smsDTO);
                notificationReq = new NotificationRequest(smsDTO.getId(), null, userDTO.getMobileNumberOrEmail(), data, NotificationTemplate.NEW_REGISTRATION, NotificationType.SMS);
            }
         //   rabbitMqService.publishSMSMessage(notificationReq);
            String successMessage = String.format("An Email Sent to the provided Email id: %s. " + "Activate your account by using code sent to your email ID", userDTO.getEmailAddress());
            SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), successMessage, "");
            modelandView.addObject("registrationSuccess", true);
            modelandView.addObject("message", successMessage);
            modelandView.addObject("userEmailId", userDTO.getEmailAddress());
            modelandView.setViewName("registrationSuccess");
            return modelandView;
        } catch (Exception e) {
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
            modelandView.addObject("message", e.getMessage());
            modelandView.addObject("registrationSuccess", false);
            modelandView.setViewName("loginFailure");
            return modelandView;
        }
    }

    @PostMapping("/activateUser")
    public ModelAndView activateAccount(UserDTO userDTO, BindingResult bindingResult) {

        logger.debug("Activating the user for the email id " + userDTO.getMobileNumberOrEmail());
        try {
            registrationService.activateUserAccount(userDTO);
            SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(),
                    "User Added Successfully. Email Sent to the provided Email id. " + "Please activate the account by clicking the link that is sent", "");
            return new ModelAndView("loginSuccess").addObject("message", "Congratulation!. Your account is active. You can login now");
        } catch (Exception e) {
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
            return new ModelAndView("loginFailure").addObject("message", e.getMessage());
        }
    }

    
    // for ajax call
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(String emailId) {
        try {
            registrationService.forgotPassword(emailId);
            SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), "User Added Successfully", "");
            return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody UserDTO userDTO, BindingResult bindingResult) {

        try {

            java.util.Date uDate = new java.util.Date();
            if (!userDTO.getPassword().equalsIgnoreCase(userDTO.getConfirmPassword())) {
                throw new Exception("Password and Confirm Password doesnt match. Please try again with correct password");
            }

            registrationService.resetPassword(userDTO);
            SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), "Password Resetted Successfully", "");
            return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/completeRegistration")
    public ResponseEntity<?> completeRegistration(@RequestBody CompleteRegistrationDTO completeRegistrationDTO, BindingResult bindingResult) {

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
                SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), "User Details added Successfully", "");
                return new ResponseEntity<>(messageHandler, HttpStatus.ACCEPTED);
            } else {
                ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Unable to store user details. Please try after some time...", "");
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

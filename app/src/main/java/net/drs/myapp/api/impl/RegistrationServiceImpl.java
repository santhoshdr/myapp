package net.drs.myapp.api.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import net.drs.myapp.api.INotifyByEmail;
import net.drs.myapp.api.INotifyBySMS;
import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dao.INotifyBySMSDAO;
import net.drs.myapp.dao.IRegistrationDAO;
import net.drs.myapp.dto.CompleteRegistrationDTO;
import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.dto.SMSDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.CompleteUserDetails;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.Otp;
import net.drs.myapp.model.OtpDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;
import net.drs.myapp.notification.NotificationDataConstants;
import net.drs.myapp.notification.NotificationRequest;
import net.drs.myapp.notification.NotificationTemplate;
import net.drs.myapp.notification.NotificationType;
import net.drs.myapp.utils.AppUtils;

@Repository("registrationService")
@Transactional
public class RegistrationServiceImpl implements IRegistrationService {

    @Autowired
    private IRegistrationDAO registrationDAO;

    @Autowired
    private INotifyBySMSDAO notifyBySMSDAO;

    
    @Autowired
    IUserDetails userDetails;

    @Autowired
    INotifyByEmail notificationByEmailService;
    
    @Autowired
    INotifyBySMS notificationBySMSService;
    
    private ModelMapper modelMapper = new ModelMapper();

    @Value("${upload.image}")
    private String uploadimage;

    @Value("${upload.image.location}")
    private String uploadImageLocation;
    
    @Value("${temperory.activationstring.valid.for.minutes}")
    private int temperoryactivationvalidtillminutes = 15; // 15 mins by default

    @Override
    public UserDTO adduser(UserDTO userDTO, Set<Role> roles) throws Exception {

            User user = new User();
            modelMapper.map(userDTO, user);
            Users storedUser  = registrationDAO.checkIfUserExistbyEmailId(user);
            if (storedUser !=null) {
                throw new Exception ("User with same email id is already present. Please try with different email id");
            }
               user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                user = registrationDAO.addUser(user, roles);
                modelMapper.map(user,userDTO);
                return userDTO;
            } 

    @Override
    public boolean addFotographer(Fotographer fotographer) {
        registrationDAO.addFotographer(fotographer);
        return true;
    }

    @Override
    public boolean completeRegistration(CompleteRegistrationDTO completeRegistrationDTO) throws Exception {
        Users users = null;
        CompleteUserDetails completeUserDetails = new CompleteUserDetails();
        modelMapper.map(completeRegistrationDTO, completeUserDetails);
        users = registrationDAO.checkIfUserPhoneisPresentandVerified(completeRegistrationDTO.getUserIdorEmailAddress());
        if (null != users) {
            completeUserDetails.setUserId(users.getId());
            registrationDAO.completeRegistration(completeUserDetails);
        } else {
            users = registrationDAO.checkIfUserPhoneisPresentandVerified(completeRegistrationDTO.getUserIdorEmailAddress());
        }
        if (users == null) {
            throw new Exception("Email or Mobile is not registered. Please register with you email id or phone number");
        }
        return false;
    }

    @Override
    public User adduserandGetId(UserDTO userDTO, Set<Role> roles) throws Exception {
        Long userId = 0L;
        User user = new User();
        
        // assume the email/phone number is present
        boolean result = true;
        try {
            modelMapper.map(userDTO, user);
            int validFor = AppUtils.getAccountValidityExpiryAfterDays();
            Users storeduser = null;
            
            
            if(!StringUtils.isEmpty(userDTO.getEmailAddress()) 
                    && AppUtils.isEmailId(userDTO.getEmailAddress())){
                storeduser = registrationDAO.checkIfUserExistbyEmailId(user);
            }else if (!StringUtils.isEmpty(userDTO.getMobileNumber()) 
                    && AppUtils.isPhoneNumber(userDTO.getMobileNumber())) {
                storeduser = registrationDAO.checkifUserExistbyPhoneNumber(user);
            }
            
            // new user
            if (storeduser == null) {
                String temperoryActivationString = AppUtils.generateRandomString();
                user.setTemporaryActivationSentDate(System.currentTimeMillis());
                user.setTemporaryActivationString(temperoryActivationString);
           //    user.setPassword(AppUtils.encryptPassword(user.getPassword()));
                user.setTemporaryActivationvalidforInMinutes(temperoryactivationvalidtillminutes);
                // Storing User
                user = registrationDAO.addUserandGetUserId(user, roles);
                File filetobeUploaded = userDTO.getImage() != null ? userDTO.getImage() : null;
                if (uploadimage != null && uploadimage.equals("folder") && filetobeUploaded != null) {
                    byte[] bytes = Files.readAllBytes(filetobeUploaded.toPath());
                    Path path = Paths.get(uploadImageLocation + File.separator + userId + "--" + filetobeUploaded.getName());
                    Files.write(path, bytes);
                }
            } else {
                throw new Exception("User with provided email / phone number is already present. Please try with different email / phonenumber.");
            }
        } catch (Exception e) {
            throw e;
        }
        return user;
    }

    
    // reset and send email
    @Override
    public String forgotPassword(String emailIdorPhoneNumber) throws Exception {

        User user = new User();
        Users storedUser  = null;
        String message = "";
        
        if(!StringUtils.isEmpty(emailIdorPhoneNumber) 
                && AppUtils.isEmailId(emailIdorPhoneNumber)){
            user.setEmailAddress(emailIdorPhoneNumber);
            storedUser = registrationDAO.checkIfUserExistbyEmailId(user);
            
            if (storedUser == null) {
                throw new Exception("Email Id  Not found");
            }
            System.out.println("Resetting password");
            String temperoryPassword = AppUtils.generateRandomString();
            storedUser.setEmail(emailIdorPhoneNumber);
            storedUser.setPassword(temperoryPassword);
            storedUser.setTempPassord(true);
           registrationDAO.updateUserWithTemperoryPassword(storedUser);
           
           NotificationRequest notificationReq = null;
            Map<String, String> data = new HashMap<String, String>();
            EmailDTO emailDto = new EmailDTO();
            java.util.Date uDate = new java.util.Date();
            emailDto.setEmailId(emailIdorPhoneNumber);
            emailDto.setCreatedBy(ApplicationConstants.USER_SYSTEM);
            emailDto.setCreationDate(new java.sql.Date(uDate.getTime()));
            emailDto.setUpdatedBy(ApplicationConstants.USER_SYSTEM);
            emailDto.setUpdatedDate(new java.sql.Date(uDate.getTime()));
            emailDto.setEmailTemplateId("FORGOTPASSWORD_EMAIL");
            emailDto.setUserID(new Long(123));
            emailDto.setNeedtoSendEmail(true);
       //     data.put(NotificationDataConstants.USER_EMAILID, emailIdorPhoneNumber);
            data.put(NotificationDataConstants.TEMPERORY_ACTIVATION_STRING, temperoryPassword);
            Long notificationId = notificationByEmailService.insertDatatoDBforNotification(emailDto);
            notificationReq = new NotificationRequest(notificationId, emailDto.getEmailId(), null, data, NotificationTemplate.FORGOT_PASSWORD, NotificationType.EMAIL);
            notificationByEmailService.sendNotoficationDirectly(notificationReq);
            message = String.format("Temperory Password has been sent to your Email id : %s. ", emailIdorPhoneNumber + ".  Use it to reset your password");
        }else if (!StringUtils.isEmpty(emailIdorPhoneNumber) 
                && AppUtils.isPhoneNumber(emailIdorPhoneNumber)) {
            user.setMobileNumber(emailIdorPhoneNumber);
            storedUser = registrationDAO.checkifUserExistbyPhoneNumber(user);
            if (storedUser != null) {
                char[] otpdigits = AppUtils.fourDigitOTPForMobileVerification();
                String smsMessage = String.format("OTP for phone verification is %s", new String(otpdigits));
                System.out.println("SMS OTP SENT :" + smsMessage);
                Otp otp = new Otp();
                otp.setOtpSentTimeStamp(AppUtils.getCurrentTimeStamp());
                otp.setUniqueOTPSent(otpdigits);
                otp.setUserId(user.getId());
                otp.setOtpValidFor(1); // assumption needs to be changed - 1 hour -
                otp.setIsvalidated(false);
                otp.setPhoneNumber(emailIdorPhoneNumber);
                notificationBySMSService.insertOTP(otp);
                SMSDTO smsDTO = new SMSDTO(user.getId(), emailIdorPhoneNumber, smsMessage);
                notificationBySMSService.sendPhoneNumberVerificationSMS(smsDTO);
                message = String.format("OTP is sent to the provided Phone Number: %s. ", emailIdorPhoneNumber + ".  Verify your phone number to activate your account ");
        }else {
            throw new Exception("Phone number not available. Please Check again.");
        }
    }
        return message;
    }

    @Override
    public boolean resetPassword(UserDTO userDTO) throws Exception {
        User user =  new User();
        Users users = null;
        
        if(AppUtils.isEmailId(userDTO.getMobileNumberOrEmail())) {
            user.setEmailAddress(userDTO.getMobileNumberOrEmail());
            users = registrationDAO.checkIfUserExistbyEmailId(user);
            
            boolean result = users.isTempPassord()  && users.getPassword().equalsIgnoreCase(userDTO.getTemporaryActivationString());
            if(!result) {
                throw new Exception("Please enter correct temporary string sent to your Email. ");
            }
            // because temperory password matches , 
            users.setPassword(userDTO.getPassword());
            if(users == null) {
                throw new Exception("Enterd Email is not present. Please register.");
            }
            registrationDAO.activateUserIftemporaryPasswordMatches(users);
            
        }else if (AppUtils.isPhoneNumber(userDTO.getMobileNumberOrEmail())) {
            user.setMobileNumber(userDTO.getMobileNumberOrEmail());
            users = registrationDAO.checkifUserExistbyPhoneNumber(user);
            if(users == null) {
                throw new Exception("Enterd Phone number is not present. Please register.");
            }
            Otp storedOtp = notifyBySMSDAO.verifyEnteredOTP(userDTO.getMobileNumberOrEmail());
            boolean result =  storedOtp.getPhoneNumber().equalsIgnoreCase(userDTO.getMobileNumberOrEmail()) && 
                    Arrays.equals(storedOtp.getUniqueOTPSent(), userDTO.getTemporaryActivationString().toCharArray());
            
            
            if(result) {
                
                OtpDTO otpDTO = new OtpDTO();
                otpDTO.setPassword(userDTO.getPassword());
                otpDTO.setPhoneNumber(userDTO.getMobileNumberOrEmail());
                otpDTO.setUserId(users.getId());
                storedOtp.setIsvalidated(true);
                notifyBySMSDAO.markPhoneNumberValidated(storedOtp);
                registrationDAO.updateUserasActive(otpDTO);
                
            }else {
                throw new Exception("Entered  OTP is wrong. Please re-verify");
            }
        }
        
        
        if (users == null) {
            throw new Exception("Email Id / Phone Number  Not found ...");
        }
        
//        userDetails.getMemberById(users.getId());
//        users.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
//        users.setTempPassord(false);
//        registrationDAO.resetPassword(users);
        return true;
    }

    @Override
    public boolean changePasswordCheckUserAvailable(String userNameOrEmailid) throws Exception {
        User user = userDetails.getUserById(userNameOrEmailid);
        if (user == null) {
            throw new Exception("User with this emailid is not present");
        } else if (user.isActive()) {
            throw new Exception("User is Not Active.");
        }
        return true;
    }

    
    // check when this method is used... 
    @Override
    public boolean activateUserAccount(UserDTO userDTO) throws Exception {
        // check if the email if exists..
        User user = new User();
        modelMapper.map(userDTO, user);

        Users  users = registrationDAO.checkIfUserEmailIdExists(user);
        if (users ==null ) {
            throw new Exception("Email id doesn't not exist. Please check your email id");
        }

        User storedUser = registrationDAO.getTemporaryActivationTokenforUser(userDTO.getEmailAddress());

        if (storedUser.getTemporaryActivationString() == null) {
            // this means, there is no temporary password set in db
            throw new Exception("Error while resetting password. Please contact administrator");
        }
        // 600000
        // zoom123 -- used only for testcases...
        if ((storedUser.getTemporaryActivationString().equalsIgnoreCase(userDTO.getTemporaryActivationString()) 
                && (System.currentTimeMillis() - storedUser.getTemporaryActivationSentDate()) < AppUtils.getActivationStringExpiryTimeInMilliseconds()))
        // time at which temporaryActivationString sent - Current time must be
        // less than 10 mins ( which is the value set as standard )
        {
        	users.setPassword(userDTO.getPassword());
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            // Getting current date
            Calendar cal = Calendar.getInstance();
            // is account expiry is set.
            if (AppUtils.enableAccountExpiry()) {
                // Displaying current date in the desired format
                System.out.println("Current Date: " + sdf.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_MONTH, AppUtils.getAccountValidityExpiryAfterDays());
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 58);
                // Date after adding the days to the current date
                storedUser.setAccountValidTill(cal.getTime());
            } else {
                // infinate
                cal.set(2100, 12, 30);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 58);
                storedUser.setAccountValidTill(cal.getTime());
            }
            
            // users - user
            registrationDAO.activateUserIftemporaryPasswordMatches(users);
        }else {
            throw new Exception("Password doesnt match or Activation Duration is expired. Please try after some time...");
        }
        return false;
    }

    @Override
    public boolean verifyOtpForPhonumber(OtpDTO otpDTO) throws Exception {

        try {
        Otp otpStored =  notifyBySMSDAO.verifyEnteredOTP(otpDTO.getPhoneNumber());
        boolean result =  otpStored.getPhoneNumber().equalsIgnoreCase(otpDTO.getPhoneNumber()) && 
                Arrays.equals(otpStored.getUniqueOTPSent(), otpDTO.getOtpNumber().toCharArray());
        
        if(result) {
            otpStored.setIsvalidated(true);
            notifyBySMSDAO.markPhoneNumberValidated(otpStored);
            registrationDAO.updateUserasActive(otpDTO);
        }else {
            throw new Exception("Please enter valid OTP sent to your phone.");
        }
        return result;
        }catch(Exception e ) {
            throw e;
        }
      }

    @Override
    public Users  checkIfUserExists(String emailorphonenumber,String loginType) throws Exception {
        User user = new User();
        if(loginType.equals("SMS")) {
            user.setMobileNumber(emailorphonenumber);
          return  registrationDAO.checkifUserExistbyPhoneNumber(user);
        }else if (loginType.equals("EMAIL")) {
            user.setEmailAddress(emailorphonenumber);
            return registrationDAO.checkIfUserExistbyEmailId(user);
        }
        return null;
    }

    /*
     * @Override public boolean addAdministrator(UserDTO userDTO) throws
     * Exception { try {
     * 
     * User user = new User();
     * 
     * modelMapper.map(userDTO, user);
     * 
     * boolean result = registrationDAO.checkIfUserExistbyName(user);
     * user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()))
     * ; if(!result){ return registrationDAO.addUser(user); }else{ throw new
     * Exception("Some problem."); }
     * 
     * 
     * } catch (Exception e) { // TODO Auto-generated catch block throw e; }
     */

}

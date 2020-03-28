package net.drs.myapp.api.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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

import net.drs.common.notifier.NotificationDataConstants;
import net.drs.common.notifier.NotificationRequest;
import net.drs.common.notifier.NotificationTemplate;
import net.drs.common.notifier.NotificationType;
import net.drs.myapp.api.INotifyByEmail;
import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dao.IRegistrationDAO;
import net.drs.myapp.dto.CompleteRegistrationDTO;
import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.CompleteUserDetails;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;
import net.drs.myapp.mqservice.RabbitMqService;
import net.drs.myapp.utils.AppUtils;

@Repository("registrationService")
@Transactional
public class RegistrationServiceImpl implements IRegistrationService {

    @Autowired
    private IRegistrationDAO registrationDAO;

    @Autowired
    RabbitMqService rabbitMqService;

    @Autowired
    IUserDetails userDetails;

    @Autowired
    INotifyByEmail notificationByEmailService;

    private ModelMapper modelMapper = new ModelMapper();

    @Value("${upload.image}")
    private String uploadimage;

    @Value("${upload.image.location}")
    private String uploadImageLocation;
    
    @Value("${temperory.activationstring.valid.for.minutes}")
    private int temperoryactivationvalidtillminutes = 15; // 15 mins by default

    @Override
    public UserDTO adduser(UserDTO userDTO, Set<Role> roles) throws Exception {

        try {
            User user = new User();
            modelMapper.map(userDTO, user);
            boolean result = registrationDAO.checkIfUserExistbyEmailId(user);
            if (!result) {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                user = registrationDAO.addUser(user, roles);
                modelMapper.map(user,userDTO);
                return userDTO;
            } else {
                throw new Exception("Some problem.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw e;
        }
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
        try {
            modelMapper.map(userDTO, user);
            int validFor = AppUtils.getAccountValidityExpiryAfterDays();
            boolean result = registrationDAO.checkIfUserExistbyEmailId(user);
            if (!result) {
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
                throw new Exception("Some problem.");
            }
        } catch (Exception e) {
            throw e;
        }
        return user;
    }

    @Override
    public String forgotPassword(String emailId) throws Exception {

        // check if user exist and is active
        Users users = registrationDAO.checkIfUserPhoneisPresentandVerified(emailId);
        if (users == null) {
            throw new Exception("Email Id Not found ...");
        }
        System.out.println("Resetting password");
        String temperoryPassword = AppUtils.generateRandomString();
        users.setEmail(emailId);
        users.setPassword(temperoryPassword);
        users.setId(users.getId());
        boolean result = registrationDAO.updateUserWithTemperoryPassword(users);
        if (!result) {
            throw new Exception("Unable to Reset Password. Kindly Try after some time. OR Contact Administrator.");
        }
        NotificationRequest notificationReq = null;
        Map<String, String> data = new HashMap<String, String>();
        
        EmailDTO emailDto = new EmailDTO();
        java.util.Date uDate = new java.util.Date();
        emailDto.setEmailId(emailId);
        emailDto.setCreatedBy(ApplicationConstants.USER_SYSTEM);
        emailDto.setCreationDate(new java.sql.Date(uDate.getTime()));
        emailDto.setUpdatedBy(ApplicationConstants.USER_SYSTEM);
        emailDto.setUpdatedDate(new java.sql.Date(uDate.getTime()));
        emailDto.setEmailTemplateId("FORGOTPASSWORD_EMAIL");
        emailDto.setUserID(new Long(123));
        emailDto.setNeedtoSendEmail(true);
        data.put(NotificationDataConstants.USER_EMAILID, emailId);
        data.put(NotificationDataConstants.TEMPERORY_ACTIVATION_STRING, temperoryPassword);
        Long notificationId = notificationByEmailService.insertDatatoDBforNotification(emailDto);
        notificationReq = new NotificationRequest(notificationId, emailDto.getEmailId(), null, data, NotificationTemplate.FORGOT_PASSWORD, NotificationType.EMAIL);
        
        
        notificationByEmailService.sendNotoficationDirectly(notificationReq);
        
   //     rabbitMqService.publishSMSMessage(new NotificationRequest(notificationId, emailId, "FORGOTPASSWORD_EMAIL", "notificationmessage"));
        return "SUCCESS";
    }

    @Override
    public boolean resetPassword(UserDTO userDTO) throws Exception {

        Users users = registrationDAO.checkIfUserPhoneisPresentandVerified(userDTO.getEmailAddress());
        if (users == null) {
            throw new Exception("Email Id Not found ...");
        }
        users.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        registrationDAO.resetPassword(users);
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

    @Override
    public boolean activateUserAccount(UserDTO userDTO) throws Exception {
        // check if the email if exists..
        User user = new User();
        modelMapper.map(userDTO, user);

        boolean result = registrationDAO.checkIfUserEmailIdExists(user);
        if (!result) {
            throw new Exception("Email if doesnt not exist. Please check your email id");
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
            
            storedUser.setPassword(userDTO.getPassword());
            
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
            registrationDAO.activateUserIftemporaryPasswordMatches(storedUser);
        }else {
            throw new Exception("Password doesnt match or Activation Duration is expired. Please try after some time...");
        }
        return false;
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

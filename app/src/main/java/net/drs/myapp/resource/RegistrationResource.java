package net.drs.myapp.resource;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import net.drs.common.notifier.NotificationDataConstants;
import net.drs.common.notifier.NotificationRequest;
import net.drs.common.notifier.NotificationTemplate;
import net.drs.common.notifier.NotificationType;
import net.drs.myapp.api.INotifyByEmail;
import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dto.CompleteRegistrationDTO;
import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.mqservice.RabbitMqService;
import net.drs.myapp.response.handler.ExeceptionHandler;
import net.drs.myapp.response.handler.SuccessMessageHandler;

@CrossOrigin
@RequestMapping("/guest")
@RestController
public class RegistrationResource extends GenericService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationResource.class);

    @Autowired
    IRegistrationService registrationService;

    @Autowired
    INotifyByEmail notificationByEmailService;

    @Autowired
    RabbitMqService rabbitMqService;

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
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO, BindingResult bindingResult, WebRequest request) {

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
            User user = registrationService.adduserandGetId(userDTO, roles);
            if (user != null && user.getUserId() > 0) {
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
                // ifsend email then ..
                notificationReq = new NotificationRequest(notificationId, emailDto.getEmailId(), null, data, NotificationTemplate.NEW_REGISTRATION, NotificationType.EMAIL);
                // else if send sms then
                // notificationReq = new NotificationRequest(notificationId,
                // null,"999999999", data,NotificationTemplate.NEW_REGISTRATION,
                // NotificationType.SMS);
                System.out.println("Reguest URL " + request.getContextPath());
                rabbitMqService.publishSMSMessage(notificationReq);
            }
            SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(),
                    "User Added Successfully. Email Sent to the provided Email id. " + "Please activate the account by clicking the link that is sent", "");
            return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/activateUser")
    public ResponseEntity<?> activateAccount(@RequestBody UserDTO userDTO, BindingResult bindingResult) {

        logger.debug("Activating the user for the email id " + userDTO.getEmailAddress());
        try {
            registrationService.activateUserAccount(userDTO);
            SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(),
                    "User Added Successfully. Email Sent to the provided Email id. " + "Please activate the account by clicking the link that is sent", "");
            return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody String emailId, BindingResult bindingResult) {

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
    @PreAuthorize("hasAnyRole('USER')")
    public String hello(@AuthenticationPrincipal Principal principal) {

        principal.getName();
        return "Hello Youtube";
    }

}

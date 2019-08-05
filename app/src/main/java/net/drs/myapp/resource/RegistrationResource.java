package net.drs.myapp.resource;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.drs.myapp.api.INotifyByEmail;
import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dto.CompleteRegistrationDTO;
import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.mqservice.NotificationRequest;
import net.drs.myapp.mqservice.RabbitMqService;
import net.drs.myapp.response.handler.ExeceptionHandler;
import net.drs.myapp.response.handler.SuccessMessageHandler;

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

@CrossOrigin
@RequestMapping("/guest")
@RestController
public class RegistrationResource extends GenericService{

    @Autowired
    IRegistrationService registrationService;

    @Autowired
    INotifyByEmail notificationByEmailService;

    @Autowired
    RabbitMqService rabbitMqService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {

        java.util.Date uDate = new java.util.Date();
        Set<Role> roles = new HashSet();
        Long notificationId = 0L;

        userDTO.setDateOfCreation(new java.sql.Date(uDate.getTime()));
        userDTO.setLastUpdated(new java.sql.Date(uDate.getTime()));
        userDTO.setCreatedBy(ApplicationConstants.USER_GUEST);
        userDTO.setCreationDate(new java.sql.Date(uDate.getTime()));
        userDTO.setUpdatedBy(ApplicationConstants.USER_GUEST);
        userDTO.setUpdatedDate(new java.sql.Date(uDate.getTime()));

        try {
            Role role = new Role();
            role.setRole(ApplicationConstants.ROLE_USER);
            roles.add(role);
            Long userId = registrationService.adduserandGetId(userDTO, roles);
            if (userId != null && userId > 0) {
                EmailDTO emailDto = new EmailDTO();
                emailDto.setEmailId(userDTO.getEmailAddress());
                emailDto.setCreatedBy(ApplicationConstants.USER_SYSTEM);
                emailDto.setCreationDate(new java.sql.Date(uDate.getTime()));
                emailDto.setUpdatedBy(ApplicationConstants.USER_SYSTEM);
                emailDto.setUpdatedDate(new java.sql.Date(uDate.getTime()));
                emailDto.setEmailTemplateId("REGISTRATION_EMAIL");
                emailDto.setUserID(new Long(123));
                emailDto.setNeedtoSendEmail(true);
                notificationId = notificationByEmailService.insertDatatoDBforNotification(emailDto);

                NotificationRequest notificationReq = new NotificationRequest(notificationId, emailDto.getEmailId(), "TEMPLATE");
                rabbitMqService.publishSMSMessage(notificationReq);
            }
            SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), "User Added Successfully", "");
            return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody String emailId, BindingResult bindingResult) {

        java.util.Date uDate = new java.util.Date();
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

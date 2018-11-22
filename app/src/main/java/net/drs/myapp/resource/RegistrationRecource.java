package net.drs.myapp.resource;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.drs.myapp.api.INotifyByEmail;
import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.response.handler.ExeceptionHandler;
import net.drs.myapp.response.handler.SuccessMessageHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/guest")
@RestController
public class RegistrationRecource {
	
	@Autowired
	IRegistrationService  registrationService;
	
	@Autowired
	INotifyByEmail notificationByEmailService;
	
	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO,BindingResult bindingResult) {
        
		java.util.Date uDate = new java.util.Date();
		Set<Role> roles = new HashSet();
		

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

			boolean result =registrationService.adduser(userDTO,roles);
			if(result){
				//sending email here.. 
				EmailDTO  emailDto = new EmailDTO();
				emailDto.setEmailId(userDTO.getEmailAddress());
				emailDto.setCreatedBy(ApplicationConstants.USER_SYSTEM);
				emailDto.setCreationDate(new java.sql.Date(uDate.getTime()));
				emailDto.setUpdatedBy(ApplicationConstants.USER_SYSTEM);
				emailDto.setUpdatedDate(new java.sql.Date(uDate.getTime()));
				emailDto.setEmailTemplateId("REGISTRATION_EMAIL");
				emailDto.setUserID(new Long(123));
				emailDto.setNeedtoSendEmail(true);
				notificationByEmailService.insertDatatoDBforNotification(emailDto);
			}
			
			
			SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(),"User Added Successfully","");
			return new ResponseEntity<>(messageHandler, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(),"");
			return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		}
		}
	
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('USER')")
	public String hello(@AuthenticationPrincipal Principal principal) {
		
		principal.getName();
		return "Hello Youtube";
    }
	
}

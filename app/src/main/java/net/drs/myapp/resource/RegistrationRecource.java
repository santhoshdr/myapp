package net.drs.myapp.resource;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.response.handler.ExeceptionHandler;
import net.drs.myapp.response.handler.SuccessMessageHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/guest")
@RestController
public class RegistrationRecource {
	
	@Autowired
	IRegistrationService  registrationService;
	
	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO,BindingResult bindingResult) {
        
		java.util.Date uDate = new java.util.Date();
		Set<Role> roles = new HashSet();
		
		userDTO.setDateOfCreation(new java.sql.Date(uDate.getTime()));
		userDTO.setLastUpdated(new java.sql.Date(uDate.getTime()));				
		try {
			Role role = new Role();
			role.setRole("USER");
			roles.add(role);
			boolean result =registrationService.adduser(userDTO,roles);
			
			SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(),"User Added Successfully","");
			return new ResponseEntity<>(messageHandler, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(),"");
			return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		}
		}
}

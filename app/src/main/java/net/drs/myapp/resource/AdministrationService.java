package net.drs.myapp.resource;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.response.handler.ExeceptionHandler;
import net.drs.myapp.response.handler.SuccessMessageHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// these methods should be only accessible by admins
@RequestMapping("/admin")
@RestController
//@PreAuthorize("hasAnyRole('ADMIN')")
public class AdministrationService {

		@Autowired
		IRegistrationService  registrationService;
	
		@Autowired
		IUserDetails userDetails;
		
		@PostMapping("/addUser")
		public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO,BindingResult bindingResult) {
	    	java.util.Date uDate = new java.util.Date();
			userDTO.setDateOfCreation(new java.sql.Date(uDate.getTime()));
			userDTO.setLastUpdated(new java.sql.Date(uDate.getTime()));				
			try {
				Set<Role> roles = new HashSet();
				Role role = new Role();
				role.setRole("ADMIN");
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
		
		
		@GetMapping("/getAllUsers")
		public ResponseEntity<?> addArticle() {

			java.util.Date uDate = new java.util.Date();
			try {
				// 10 is not used any where as of now.. Need to use this if performance degrades
				 List<UserServiceDTO> userDTO  = userDetails.getAllUsers(10);
				 return new ResponseEntity<>(userDTO, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.","");
				return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
			}
		}

		
		@GetMapping("/getAllActiveUsers")
		public ResponseEntity<?> getAllActiveUsers() {

			java.util.Date uDate = new java.util.Date();
			try {
				 List<UserServiceDTO> userDTO  = userDetails.getAllActiveUsers(10);
				 return new ResponseEntity<>(userDTO, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.","");
				return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
			}
		}

	
		@GetMapping("/getAllAdminActiveUsers")
		public ResponseEntity<?> getAllAdminActiveUsers() {

			java.util.Date uDate = new java.util.Date();
			try {
				 List<UserServiceDTO> userDTO  = userDetails.getAllAdminActiveUsers(10);
				 return new ResponseEntity<>(userDTO, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.","");
				return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
			}
		}
		
		
		
		
		
		
		@GetMapping("/all")
	    public String hello() {
	        return "Hello Youtube";
	    }

	    @GetMapping("/secured/all")
	    public String securedHello(Principal princi) {
	    	userDetails.activeteUser(new Long(123));
	        return "Secured Hello";
	    }

	    @GetMapping("/secured/alternate")
	    public String alternate() {
	        return "alternate";
	    }
	
}

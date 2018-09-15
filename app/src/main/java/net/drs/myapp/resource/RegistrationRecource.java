package net.drs.myapp.resource;

import java.util.Date;

import net.drs.myapp.api.IRegistrationService;
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


@RequestMapping("/guest/register")
@RestController
public class RegistrationRecource {
	
	@Autowired
	IRegistrationService  registrationService;
	
	

	@PostMapping("/addUser")
	public ResponseEntity<?> addArticle(@RequestBody User user,BindingResult bindingResult) {
        
		java.util.Date uDate = new java.util.Date();

	//	user.setAddress("Bangalore");
		user.setDateOfCreation(new java.sql.Date(uDate.getTime()));
	//	user.setEmailAddress("abc@abc.com");
    //	user.setFirstName(user.getFirstName());
	//	user.setLastName("LastName");
	//  user.setMobileNumber("1234567889");
	//  user.setPassword(user.getPassword());
		user.setLastUpdated(new java.sql.Date(uDate.getTime()));				
		
		try {
			boolean result =registrationService.adduser(user);
			
			SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(),"User Added Successfully","");
			
			return new ResponseEntity<>(messageHandler, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(),"");
			return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
		}
	/*	if(result){
			sendEmailNotification.sendEmailNotification("sendEmailNotification", user);
			
			sendOTP.verifyOTPGenerate(user,5,5);	
		}
		*/
		
		
		////return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		
		
		
		
		
		
		
		/*
		boolean flag = articleService.addArticle(article);
        if (flag == false) {
        	return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/article/{id}").buildAndExpand(article.getArticleId()).toUri());
        ////
*/	}
}

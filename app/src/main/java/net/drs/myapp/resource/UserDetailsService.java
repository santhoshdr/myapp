package net.drs.myapp.resource;

import java.util.Date;
import java.util.List;

import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.response.handler.ExeceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/user")
@RestController
public class UserDetailsService {


}

	


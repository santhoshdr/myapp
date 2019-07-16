package net.drs.myapp.resource;

import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class UserDetailsService  extends GenericService{
    

    @Autowired
    IUserDetails userDetails;


    @PostMapping("/getMyProfile")
    public ResponseEntity<User> getMyProfile(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        return new ResponseEntity<>(userDetails.getUserById(userDTO.getUserId()), HttpStatus.OK);
        
    }
    
    @PostMapping("/updateMyProfile")
    public ResponseEntity<Boolean> updateMyProfile(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        
        User user = new User();
        user.setId(getLoggedInUser());
        user.setAddress(userDTO.getAddress());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMobileNumber(userDTO.getMobileNumber());
        return new ResponseEntity<>(userDetails.updateUserDetails(user), HttpStatus.OK);
        
    }
    
    
}

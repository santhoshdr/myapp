package net.drs.myapp.resource.internalui;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.config.UserPrincipal;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.model.User;
import net.drs.myapp.resource.GenericService;
import net.drs.myapp.response.handler.ExeceptionHandler;
import net.drs.myapp.response.handler.SuccessMessageHandler;
import net.drs.myapp.utils.AppUtils;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class UserDetailsService extends GenericService {

    @Autowired
    IUserDetails userDetails;

    @GetMapping("/loginHome")
    public ModelAndView hello(HttpSession session) {
        ModelAndView modelandView = new ModelAndView();
        setValueInUserSession(session,getLoggedInUserName());
        modelandView.setViewName("loginSuccess");
        return modelandView;
    }
    
    
    @GetMapping("/hello")
    public ModelAndView loginHome() {
        ModelAndView h = new ModelAndView();
        h.setViewName("hh.jsp");
        return h;

    }
    
    
    
    @GetMapping("/getMyProfile")
    public ModelAndView getMyProfile() {
        return new ModelAndView("viewProfile").
                addObject("data",userDetails.getUserById(getLoggedInUserId()));
    }

    @PostMapping("/updateMyProfile")
    public ResponseEntity<Boolean> updateMyProfile(@RequestBody UserDTO userDTO, BindingResult bindingResult) {

        User user = new User();
        user.setId(getLoggedInUserId());
        user.setAddress(userDTO.getAddress());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMobileNumber(userDTO.getMobileNumber());
        return new ResponseEntity<>(userDetails.updateUserDetails(user), HttpStatus.OK);

    }

    /**
     * User must be a logged in user to change the password
     * 
     * @param passwordDTO
     * @param bindingResult
     * @return
     */
    
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(ResetPasswordDTO passwordDTO, BindingResult bindingResult) {
        java.util.Date uDate = new java.util.Date();
        try {
            final UserPrincipal loggedInUser = getLoggedInUser();
            if (!BCrypt.checkpw(passwordDTO.getCurrentPassword(), loggedInUser.getPassword())) {
                throw new Exception("Entered Password doesnt match with entered password");
            }
            passwordDTO.setUserId(loggedInUser.getId());
            passwordDTO.setEncryptedPassword(AppUtils.encryptPassword(passwordDTO.getNewPassword()));
            userDetails.changePassword(passwordDTO);
            SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), "Password has been changed successfully", "");
            return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
            
        } catch (Exception e) {
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), e.getMessage(), "");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createWedProfile")
    public ResponseEntity<WedDTO> createWedProfile(@RequestBody WedDTO wedDTO, BindingResult bindingResult) {
        try {
            wedDTO.setWedFullName("Full Name");
            wedDTO.setCreatedBy(getLoggedInUser().toString());
            wedDTO.setUpdatedBy(getLoggedInUser().toString());
            wedDTO.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
            wedDTO.setUpdatedDate(new java.sql.Date(System.currentTimeMillis()));

            return new ResponseEntity<WedDTO>(userDetails.createWedProfile(wedDTO, getLoggedInUserId()), HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/fetchWedProfile")
    public ResponseEntity<List<WedDTO>> fetchWedProfile() {
        try {
            Long loggedInUser = getLoggedInUserId();
            return new ResponseEntity<List<WedDTO>>(userDetails.fetchWedProfile(loggedInUser), HttpStatus.OK);
        } catch (Exception e) {

        }
        return null;
    }

    @PostMapping("/updateWedProfile")
    public ResponseEntity<WedDTO> updateWedProfile(@RequestBody WedDTO wedDTO, BindingResult bindingResult) {
        try {
            Long loggedInUser = getLoggedInUserId();
            return new ResponseEntity<WedDTO>(userDetails.updateWedProfile(wedDTO, loggedInUser), HttpStatus.OK);
        } catch (Exception e) {

        }
        return null;
    }

}

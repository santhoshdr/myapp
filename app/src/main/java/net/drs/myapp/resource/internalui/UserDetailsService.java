package net.drs.myapp.resource.internalui;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

@RestController
@RequestMapping("/v1/user")
//@PreAuthorize("hasAnyRole('ROLE_USER')")
public class UserDetailsService extends GenericService {

    @Autowired
    IUserDetails userDetails;

    @PostMapping("/hello")
    public ModelAndView hello(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        ModelAndView h = new ModelAndView();
        h.setViewName("hh.jsp");
        return h;

    }
    
    @PostMapping("/getMyProfile")
    public ResponseEntity<User> getMyProfile(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        return new ResponseEntity<>(userDetails.getUserById(userDTO.getUserId()), HttpStatus.OK);

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
    public ResponseEntity<?> changePassword(@RequestBody ResetPasswordDTO passwordDTO, BindingResult bindingResult) {

        java.util.Date uDate = new java.util.Date();
        try {

            final UserPrincipal loggedInUser = getLoggedInUser();
            final String storedPasswoed = AppUtils.encryptPassword(passwordDTO.getCurrentPassword());

            if (storedPasswoed.equalsIgnoreCase(loggedInUser.getPassword())) {
                throw new Exception("Entered Password doesnt match with entered password");
            }
            passwordDTO.setUserId(loggedInUser.getId());
            passwordDTO.setEncryptedPassword(AppUtils.encryptPassword(passwordDTO.getNewPassword()));
            userDetails.changePassword(passwordDTO);
            SuccessMessageHandler messageHandler = new SuccessMessageHandler(new Date(), "User Added Successfully", "");
            return new ResponseEntity<>(messageHandler, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
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

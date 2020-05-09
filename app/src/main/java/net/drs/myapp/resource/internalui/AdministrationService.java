package net.drs.myapp.resource.internalui;

import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.resource.GenericService;
import net.drs.myapp.response.handler.ExeceptionHandler;
import net.drs.myapp.response.handler.SuccessMessageHandler;
import net.drs.myapp.utils.AppUtils;

// these methods should be only accessible by admins
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class AdministrationService extends GenericService {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    IRegistrationService registrationService;

    @Autowired
    IUserDetails userDetails;

    @PostMapping("/addAdmin")
    public ResponseEntity<?> addUser(@AuthenticationPrincipal Principal principal, @RequestBody UserDTO userDTO, BindingResult bindingResult) {
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

    @GetMapping("/getAllActiveUsers")
    public ResponseEntity<?> getAllActiveUsers() {

        java.util.Date uDate = new java.util.Date();
        try {
            List<UserServiceDTO> userDTO = userDetails.getAllActiveUsers(10);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllAdminActiveUsers")
    public ResponseEntity<?> getAllAdminActiveUsers() {

        httpSession.getCreationTime();
        java.util.Date uDate = new java.util.Date();
        try {
            List<UserServiceDTO> userDTO = userDetails.getAllAdminActiveUsers(10);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }
    /*
     * @PostMapping("/deactivateUser/{userId}") //
     * http://localhost:8085/name/abc/123
     * 
     * @RequestMapping(path="/{name}/{age}") public ResponseEntity<?>
     * deactivateUser(@PathVariable("userId") String name) {
     * 
     * java.util.Date uDate = new java.util.Date(); try { UserDTO userDTO = new
     * UserDTO(); userDTO.setUpdatedBy(Long.toString(getLoggedInUserId()));
     * userDTO.setUpdatedDate(AppUtils.getCurrentDate());
     * userDetails.deactiveUser(userDTO); return new ResponseEntity<>(userDTO,
     * HttpStatus.OK); } catch (Exception e) { e.printStackTrace();
     * ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(),
     * "Something not working. Try after some time.", ""); return new
     * ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND); } }
     */

    @PostMapping("/activateUser")
    // http://localhost:8085/name/abc/123
    /* @RequestMapping(path="/{name}/{age}") */
    public ResponseEntity<?> activateUser(Long userId) {

        try {
            Long updatedBy = getLoggedInUserId();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userId);
            userDTO.setActive(true);
            userDTO.setUpdatedBy(Long.toString(updatedBy));
            userDTO.setUpdatedDate(AppUtils.getCurrentDate());
            userDetails.activeteUser(userDTO);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    
    
    
    @PostMapping("/deactivateUser")
    public ModelAndView  deactivateUser(Long userId) {

        try {
            Long updatedBy = getLoggedInUserId();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userId);
            userDTO.setActive(false);
            userDTO.setUpdatedBy(Long.toString(updatedBy));
            userDTO.setUpdatedDate(AppUtils.getCurrentDate());
            userDetails.deactiveUser(userDTO);
            return new ModelAndView("redirect:/admin/getAllActiveUsers");
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return null;
        }
    }
    
    
    @PostMapping("/makeorRemoveAdmin")
    public ModelAndView  makeorRemoveAdmin(Long userId) {
        try {
            Long updatedBy = getLoggedInUserId();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userId);
            userDTO.setUpdatedBy(Long.toString(updatedBy));
            userDTO.setUpdatedDate(AppUtils.getCurrentDate());
            userDetails.makeorremoveAdmin(userDTO);
            return new ModelAndView("redirect:/admin/getAllActiveUsers");
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return null;
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    /*
     * 
     * { "userId":"2", "roles":[{"roleId":6,"role":"ADMIN"}]
     * 
     * }
     */
    @PutMapping("/changeUserRole")
    public ResponseEntity<?> getAllUsersWithRoles(@AuthenticationPrincipal Principal principal, @RequestBody UserServiceDTO userServiceDTO) {
        try {

            String updatedBy = principal != null ? principal.getName() : ApplicationConstants.USER_SYSTEM;

            java.util.Date uDate = new java.util.Date();
            userServiceDTO.setUpdatedBy(updatedBy);
            userServiceDTO.setUpdatedDate(new java.sql.Date(uDate.getTime()));

            userDetails.changeUserRole(userServiceDTO);

            return new ResponseEntity<>("", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String hello(@AuthenticationPrincipal Principal principal) {

        principal.getName();
        return "Hello Youtube";
    }

    @GetMapping("/secured/all")
    public String securedHello(Principal princi) {
        return "Secured Hello";
    }

    @GetMapping("/secured/alternate")
    public String alternate() {
        return "alternate";
    }

    @GetMapping("/getAllMembers")
    public ModelAndView getAllMembers() {
        try {
            // 10 is not used any where as of now.. Need to use this if
            // performance degrades
            List<User> userDTO = userDetails.getAllMembers(10);
            return new ModelAndView("loginSuccess").addObject("listofusers", userDTO).addObject("pageName", "viewMembers");
        } catch (Exception e) {
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ModelAndView("loginSuccess").addObject("listofusers", errorDetails);
        }
    }

    @GetMapping("/getAllUsers")
    public ModelAndView getAllUsers() {

        java.util.Date uDate = new java.util.Date();
        try {
            // 10 is not used any where as of now.. Need to use this if
            // performance degrades
            List<UserServiceDTO> userDTO = userDetails.getAllUsers(10);
            return new ModelAndView("loginSuccess").addObject("listofusers", userDTO).addObject("pageName", "getAllUsers");
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ModelAndView("loginSuccess").addObject("listofusers", errorDetails);
        }
    }

    /*
     * // those who can login
     * 
     * @GetMapping("/getAllUsers") public ModelAndView getAllUsers() { try { //
     * 10 is not used any where as of now.. Need to use this if // performance
     * degrades List<UserServiceDTO> userDTO = userDetails.getAllUsers(10);
     * return new ModelAndView("loginSuccess").addObject("listofusers",
     * userDTO).addObject("pageName", "viewMembers"); } catch (Exception e) {
     * ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(),
     * "Something not working. Try after some time.", ""); return new
     * ModelAndView("loginSuccess").addObject("listofusers", errorDetails); } }
     */
}

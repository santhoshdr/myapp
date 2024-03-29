package net.drs.myapp.resource.internalui;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.drs.myapp.api.IMatrimonyService;
import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.exceptions.MatrimonialException;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;
import net.drs.myapp.model.Wed;
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
    IMatrimonyService matrimonialService;

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

    
    
    @PostMapping("/addRoletoUser")
    public ModelAndView  makeorRemoveAdmin(Long userId,String roleNme) {
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
            net.drs.myapp.utils.Role[]  listofRoles = net.drs.myapp.utils.Role.values();
            List<Users> users= userDetails.getAllUsers(10);
            
            for (Users userServiceDTO : users) {
            	Iterator it = userServiceDTO.getRoles().iterator();
            	
            	while(it.hasNext()) {
            		Role associatedRole = (Role)it.next();
            		if(associatedRole.getRole().equalsIgnoreCase(net.drs.myapp.utils.Role.ADMIN.getRole())) {
            			userServiceDTO.getAssociatedRoles().add(net.drs.myapp.utils.Role.ADMIN.getRoleDisplayName());
            		}else if(associatedRole.getRole().equalsIgnoreCase(net.drs.myapp.utils.Role.MATRIMONY.getRole())) {
            			userServiceDTO.getAssociatedRoles().add(net.drs.myapp.utils.Role.MATRIMONY.getRoleDisplayName());
                    }else if(associatedRole.getRole().equalsIgnoreCase(net.drs.myapp.utils.Role.USER.getRole())) {
                    	userServiceDTO.getAssociatedRoles().add(net.drs.myapp.utils.Role.USER.getRoleDisplayName());
                    }
            		
            		
            	}
			}
            
            return new ModelAndView("loginSuccess").
                                     addObject("listofusers", users).
                                     addObject("pageName", "getAllUsers").
                                     addObject("listOfRoles", listofRoles);
        } catch (Exception e) {
            e.printStackTrace();
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ModelAndView("loginSuccess").addObject("listofusers", errorDetails);
        }
    }

    
    @PostMapping("/changeRole")
    public ModelAndView changeRole(String[] newRole,Long  userId,RedirectAttributes redirectAttributes) {
    	
    	
        try {
            userDetails.updateUserRole(userId, Arrays.asList(newRole), "updateRole");
            return new ModelAndView("redirect:/admin/getAllUsers");
        } catch (Exception e) {
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ModelAndView("loginSuccess").addObject("listofusers", errorDetails);
        }
    }

    
    
    @GetMapping("/getUserRoles")
    public ResponseEntity<?> getUserRoles(Long  userId) {
    	
    	List<String> listofRolesAssociatedwithUser = new ArrayList<String>();
        try {
            Users user = userDetails.getUsersById(userId);
            
            Iterator it = user.getRoles().iterator();
            while(it.hasNext()) {
            Role role1 = (Role)it.next();
           	String roleName = role1.getRole();
           	
           	
           	// this code needs to be re-looked.
           	if(roleName.equalsIgnoreCase(net.drs.myapp.utils.Role.ADMIN.getRole())) {
           		listofRolesAssociatedwithUser.add(net.drs.myapp.utils.Role.ADMIN.getRoleDisplayName());
           	}else if(roleName.equalsIgnoreCase(net.drs.myapp.utils.Role.MATRIMONY.getRole())) {
               		listofRolesAssociatedwithUser.add(net.drs.myapp.utils.Role.MATRIMONY.getRoleDisplayName());
            }else if(roleName.equalsIgnoreCase(net.drs.myapp.utils.Role.USER.getRole())) {
           		listofRolesAssociatedwithUser.add(net.drs.myapp.utils.Role.USER.getRoleDisplayName());
            }
            }
            return new ResponseEntity<>(listofRolesAssociatedwithUser, HttpStatus.OK);
        } catch (Exception e) {
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ResponseEntity<>(listofRolesAssociatedwithUser, HttpStatus.BAD_REQUEST);

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
    
    
    @GetMapping("/deactivateWedProfile/{id}")
    public ModelAndView deactivateWedProfile(@PathVariable("id") Long wedid,RedirectAttributes redirectAttributes) throws MatrimonialException {
        
        WedDTO weddto = new WedDTO();
        weddto.setId(wedid);
        weddto.setIsProfileActive(false);
        weddto.setUpdatedBy(getLoggedInUserName());
        Wed wed = matrimonialService.activatedeactivateWed(weddto);
        redirectAttributes.addFlashAttribute("successMessage", "Profile Deactivates Successfully");
        return new ModelAndView("redirect:/matrimony/getAllWedProfiles");
    }
    
    @GetMapping("/activateWedProfile/{id}")
    public ModelAndView activateWedProfile(@PathVariable("id") Long wedid,RedirectAttributes redirectAttributes) throws MatrimonialException {
        
        WedDTO weddto = new WedDTO();
        weddto.setIsProfileActive(true);
        weddto.setId(wedid);
        weddto.setUpdatedBy(getLoggedInUserName());
        Wed wed = matrimonialService.activatedeactivateWed(weddto);
        redirectAttributes.addFlashAttribute("successMessage", "Profile Deactivates Successfully");
        return new ModelAndView("redirect:/matrimony/getAllWedProfiles");
    }
    
}

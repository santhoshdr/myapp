package net.drs.myapp.resource.internalui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.config.UserPrincipal;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.exceptions.UserException;
import net.drs.myapp.model.User;
import net.drs.myapp.resource.GenericService;
import net.drs.myapp.response.handler.ExeceptionHandler;
import net.drs.myapp.response.handler.SuccessMessageHandler;
import net.drs.myapp.utils.AppUtils;
import net.drs.myapp.utils.ClassOfMembership;
import net.drs.myapp.utils.Gotras;
import net.drs.myapp.utils.MaritalStatus;
import net.drs.myapp.utils.ModeOfPayment;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class UserDetailsService extends GenericService {

    @Autowired
    IUserDetails userDetails;

    @GetMapping("/loginHome")
    public ModelAndView hello(HttpSession session) {
        ModelAndView modelandView = new ModelAndView();
        setValueInUserSession(session, getLoggedInUserName());
        modelandView.addObject("pageName", "loginHome");
        modelandView.setViewName("loginSuccess");
        return modelandView;
    }

    @GetMapping("/hello")
    public ModelAndView loginHome() {
        ModelAndView h = new ModelAndView();
        h.setViewName("hh.jsp");
        return h;

    }

    @GetMapping("/getAllActiveMembers")
    public ModelAndView getAllActiveMembers() {
        try {
            // 10 is not used any where as of now.. Need to use this if
            // performance degrades
            List<User> userDTO = userDetails.getAllActiveMembers();
            return new ModelAndView("loginSuccess").addObject("listofusers", userDTO).addObject("pageName", "viewMembers");
        } catch (Exception e) {
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ModelAndView("loginSuccess").addObject("listofusers", errorDetails);
        }
    }

    @GetMapping("/viewMember")
    public ResponseEntity<?> viewMember(Long userId) {
        try {
            // 10 is not used any where as of now.. Need to use this if
            // performance degrades
            User user = userDetails.getMemberById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            ExeceptionHandler errorDetails = new ExeceptionHandler(new Date(), "Something not working. Try after some time.", "");
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getMyProfile")
    public ModelAndView getMyProfile() {
        return new ModelAndView("viewProfile").addObject("data", userDetails.getMemberById(getLoggedInUserId()));
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
                throw new UserException("UDE001", "Entered Password doesnt match with entered password");
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

    @GetMapping("/registerWedProfile")
    public ModelAndView registerWedProfile(RedirectAttributes redirectAttributes) {
        try {

            Gotras[] listofGotram = Gotras.values();
            List<String> genders = Arrays.asList("Male", "Female", "Transgender");
            MaritalStatus[] listOfMaritalStatus = MaritalStatus.values();
            return new ModelAndView("loginSuccess").addObject("pageName", "registerWedProfile").addObject("gotrams", listofGotram).addObject("listOfMaritalStatus", listOfMaritalStatus)
                    .addObject("genders", genders);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/createWedProfile")
    public ModelAndView createWedProfile(WedDTO wedDTO, RedirectAttributes redirectAttributes) {
        try {
            wedDTO.setCreatedBy(Long.toString((getLoggedInUser().getId())));
            wedDTO.setUpdatedBy(Long.toString(getLoggedInUser().getId()));
            wedDTO.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
            wedDTO.setUpdatedDate(new java.sql.Date(System.currentTimeMillis()));
            wedDTO = userDetails.createWedProfile(wedDTO, getLoggedInUserId());
            redirectAttributes.addFlashAttribute("successMessage", "Profile has been registered successfully");
            return new ModelAndView("redirect:/user/registerWedProfile");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was some problem in registering. Please try later");
            return new ModelAndView("redirect:/user/registerWedProfile");
        }
    }

    @GetMapping("/getMyWedProfiles")
    public ModelAndView getMyWedProfiles(RedirectAttributes redirectAttributes) {
        try {
            Long loggedInUser = getLoggedInUserId();
            List<WedDTO> wedDto = userDetails.fetchMyWedProfiles(loggedInUser,null);
            return new ModelAndView("loginSuccess").addObject("pageName", "viewMywedProfile").addObject("wedProfileList", wedDto);
        } catch (Exception e) {

        }
        return null;
    }

    // @RequestMapping(value = "/imageuploadfolder", method = RequestMethod.GET)
    // public void getImageAsByteArray(HttpServletResponse response) throws
    // IOException {
    // InputStream in =
    // servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
    // response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    // IOUtils.copy(in, response.getOutputStream());
    // }
    // imageuploadfolder/8-oCbU1kbj/images/SuccessTestCases.png
    @GetMapping(value = "/imageuploadfolder/{folderid}/{image}/{photoName}")
    public @ResponseBody  void  getImage(@PathVariable("folderid") String folderid,
            @PathVariable("image") String image,
            @PathVariable("photoName") String photoName,
            HttpServletResponse response,HttpServletRequest request) throws IOException {
        response.setContentType("image/jpeg");
        Path pathImages = Paths.get(".");
       
        
        // pwd/home/srajanna/myprojectwork/myapp/app/imageuploadfolder/
        String imagePath = "/home/srajanna/myprojectwork/myapp/app"+"/imageuploadfolder/"+folderid+"/"+image+"/"+photoName;
        Path pathImages1= Paths.get( imagePath );

        File imgFile = new File(pathImages1.toString());
        imgFile.getAbsolutePath();
        imgFile.exists();
        InputStream targetStream = new FileInputStream(imgFile);
        IOUtils.copy(targetStream, response.getOutputStream());
    }

    
    
    
    
    @GetMapping("/viewWedProfile/{id}")
    public ModelAndView viewWedProfile(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        try {
            Long loggedInUser = getLoggedInUserId();
            List<WedDTO> wedDto = userDetails.fetchWedProfile(loggedInUser, id,false);
            Gotras[] listofGotram = Gotras.values();
            List<String> genders = Arrays.asList("Male", "Female", "Transgender");
            MaritalStatus[] listOfMaritalStatus = MaritalStatus.values();
            return new ModelAndView("loginSuccess").
                    addObject("pageName", "viewWedProfile").
                    addObject("wedProfileList", wedDto).
                    addObject("gotrams", listofGotram).
                    addObject("listOfMaritalStatus", listOfMaritalStatus).
                    addObject("genders", genders);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return new ModelAndView("redirect:/user/getMyWedProfiles");
        }
    }

    //http://localhost:8085/user/deletePhoto/imageuploadfolder/8-oCbU1kbj/images/Screenshot%20from%202019-08-30%2009-47-17.png
    @PostMapping("/updateWedProfile")
    public ModelAndView updateWedProfile(WedDTO wedDTO ,
            RedirectAttributes redirectAttributes) {
        try {
            Long loggedInUser = getLoggedInUserId();
            userDetails.updateWedProfile(wedDTO, loggedInUser);
            redirectAttributes.addFlashAttribute("successMessage", "The Porfile has been updated successfully");
            return new ModelAndView("redirect:/user/viewWedProfile/"+wedDTO.getId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "There was an error while deleting the photo. Please try after sometime.");
            return new ModelAndView("redirect:/user/viewWedProfile/"+wedDTO.getId());
        }
        
    }
    //imageuploadfolder/8-oCbU1kbj/images/Screenshot%20from%202019-08-30%2009-47-17.png
    @PostMapping("/deletePhoto")
    public ModelAndView deletePhoto(String photoname,RedirectAttributes redirectAttributes) {
        String computedfolderName="";
        String computedPhotoName ="";
        WedDTO wedDTO = null;
        try {
            if(photoname.isEmpty() || photoname == null) {
                throw new Exception("Unable to delete the photo");
            }
            photoname = URLDecoder.decode(photoname,"UTF-8");
            computedfolderName = StringUtils.substringBetween(photoname, "imageuploadfolder/", "/images");
            computedPhotoName = StringUtils.substringAfterLast(photoname,"images/");
            
            wedDTO = userDetails.deletePhoto(computedPhotoName,computedfolderName, getLoggedInUserId(),null);
            
             redirectAttributes.addFlashAttribute("successMessage", "Selected Photo was deleted successfully");
            return new ModelAndView("redirect:/user/viewWedProfile/"+ wedDTO.getId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Image was not able to delete. Please try after some time");
            return new ModelAndView("redirect:/user/viewWedProfile/"+wedDTO.getId());
        }
    }
    
    @GetMapping("/deleteFile/{wedId}/{fileName}")
    public ModelAndView deleteFile(@PathVariable("wedId") long wedId,
                                                                         @PathVariable("fileName") String fileName,
                                                                         RedirectAttributes redirectAttributes) {
        WedDTO wedDTO = null;
        try {
            if(fileName.isEmpty() || fileName == null) {
                throw new Exception("Unable to delete the photo");
            }
            boolean result =   userDetails.deleteFile(fileName, wedId,getLoggedInUserId());
            if(result) {
                redirectAttributes.addFlashAttribute("successMessage", "Selected File  was deleted successfully");
                return new ModelAndView("redirect:/user/viewWedProfile/"+ wedId);
            }else {
                redirectAttributes.addFlashAttribute("errorMessage", "File  was not able to delete. Please try after some time");
                return new ModelAndView("redirect:/user/viewWedProfile/"+wedId);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "File  was not able to delete. Please try after some time");
            return new ModelAndView("redirect:/user/viewWedProfile/"+wedId);
        }
    }
    
    
    
    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<?> downloadFile(HttpServletRequest request, 
            HttpServletResponse response, 
            @PathVariable("fileName") String fileName,RedirectAttributes redirectAttributes) {
        WedDTO wedDTO = null;
        try {
            if(fileName.isEmpty() || fileName == null) {
                throw new Exception("Unable to downalod  the file");
            }

            wedDTO = userDetails.downloadFile(fileName,getLoggedInUserId(),null);
            
            String fileBasePath = wedDTO.getWedJatakaFilePath()[0];
            
            Path path = Paths.get(fileBasePath);
            Resource resource = null;
                resource = new UrlResource(path.toUri());
           
                
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "attachment; filename="+fileName);
              
                    Files.copy(path, response.getOutputStream());
                    response.getOutputStream().flush();
              
        }  catch (IOException ex) {
            ex.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        
                
       
        /*    
            
            
            
             redirectAttributes.addFlashAttribute("successMessage", "Selected Photo was deleted successfully");
            return new ModelAndView("redirect:/user/viewWedProfile/"+ wedDTO.getId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Image was not able to delete. Please try after some time");
            return new ModelAndView("redirect:/user/viewWedProfile/"+wedDTO.getId());
        }*/
        return null;
    }
    
    

    @GetMapping("/addMember")
    // get add member page
    public ModelAndView addMember(RedirectAttributes redirectAttributes) {
        try {

            Gotras[] listofGotram = Gotras.values();
            ClassOfMembership[] list = ClassOfMembership.values();
            ModeOfPayment[] modeofPayments = ModeOfPayment.values();
            ModelAndView mv = new ModelAndView("loginSuccess");
            mv.addObject("pageName", "addMember");
            mv.addObject("gotrams", listofGotram);
            mv.addObject("classOfMembership", list);
            mv.addObject("modeOfPayment", modeofPayments);
            return mv;
        } catch (Exception e) {

        }
        return null;

    }

    @PostMapping("/saveMember")
    public ModelAndView saveMember(UserDTO user, RedirectAttributes redirectAttributes) {
        try {

            validateInputRequest(user);
            user.setMemberAddedBy(getLoggedInUserId());
            user.setCreatedBy(Long.toString(getLoggedInUserId()));
            user.setCreationDate(AppUtils.getCurrentDate());
            user.setUpdatedDate(AppUtils.getCurrentDate());
            user.setUpdatedBy(Long.toString(getLoggedInUserId()));

            UserDTO userdto = userDetails.addMember(user);
            return new ModelAndView("redirect:/user/addMember").addObject("addMember", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return new ModelAndView("redirect:/user/addMember");
        }
    }

    private void validateInputRequest(UserDTO user) throws Exception {
        if (StringUtils.isEmpty(user.getFirstName())) {
            throw new Exception("Name Can't be Empty");
        } else if (StringUtils.isEmpty(user.getAddress())) {
            throw new Exception("Address Can't be Empty");
        //} else if (StringUtils.is(user.getAge())) {
       //     throw new Exception("Age Can't be Empty");
        //} else if (StringUtils.isEmpty(user.getAmount())) {
           // throw new Exception("Amount Can't be Empty");
        } else if (StringUtils.isEmpty(user.getClassofMembershipDesired())) {
            throw new Exception("Class of Membership  Can't be Empty");
        } else if (StringUtils.isEmpty(user.getGotram())) {
            throw new Exception("Gotram Can't be Empty");
        }

    }

}

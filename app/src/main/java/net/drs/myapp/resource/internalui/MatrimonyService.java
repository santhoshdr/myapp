package net.drs.myapp.resource.internalui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.drs.myapp.api.IMatrimonyService;
import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.exceptions.MatrimonialException;
import net.drs.myapp.model.Wed;
import net.drs.myapp.resource.GenericService;

@Controller
@RequestMapping("/matrimony")
@PreAuthorize("hasAnyRole('ROLE_MATRIMONY')")
//@PreAuthorize("hasAnyRole('ROLE_MATRIMONY')")
public class MatrimonyService extends GenericService{
    
    
    private static final Logger LOG = LoggerFactory.getLogger(MatrimonyService.class);

    @Autowired
    IMatrimonyService matrimonialService;
    
    @Autowired
    IUserDetails userDetails;
    
    @GetMapping("/viewActiveProfiles")
    public ModelAndView viewActiveProfiles() throws MatrimonialException {
        
        List list = matrimonialService.viewActiveProfiles();
        
        return new ModelAndView("loginSuccess").
                addObject("pageName","viewAllWedProfiles").
                addObject("wedProfiles",list);
    }
    
    
    @GetMapping("/getAllWedProfiles")
    public ModelAndView getAllWedProfiles() throws MatrimonialException {
        
        List<Wed> list = matrimonialService.getAllWedProfiles();
        
        return new ModelAndView("loginSuccess").
                addObject("pageName","viewAllWedProfiles").
                addObject("wedProfiles",list);
    }
    
    
    @GetMapping("/viewWedProfile/{id}")
    public ModelAndView viewWedProfile(@PathVariable("id") long wedId, RedirectAttributes redirectAttributes) {
        try {
            Long loggedInUser = getLoggedInUserId();
            WedDTO wedDto = userDetails.fetchSelectedWedProfile(wedId);
            return new ModelAndView("loginSuccess").
                    addObject("pageName", "matromonialServiceViewWedProfile").
                    addObject("wedProfile", wedDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return new ModelAndView("redirect:/user/getMyWedProfiles");
        }
    }
    

    
    // need to check
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
        
        return null;
    }
    
}

package net.drs.myapp.resource.internalui;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.resource.GenericService;

@Controller
public class HomeResource  extends GenericService{

    @Autowired
    IUserDetails userDetails;

    @GetMapping(value = { "", "/", "/guest" })
    public String showHome( HttpSession httpSession,RedirectAttributes redirectAttributes) {
        httpSession.invalidate();
        return "redirect:/home/guest";
    }
    /*
     * @GetMapping("/home/guest") public String loadHomePage() { return
     * "welcome"; }
     */
    @GetMapping("/home/guest")
    public ModelAndView loadHomePage() {
        return new ModelAndView("welcome");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/all")
    public String securedHello(Principal princi) {
        return "Secured Hello";
    }

    // @PreAuthorize("hasAnyRole('USER1')")
    @GetMapping("/secured/alternate")
    public String alternate() {
        return "alternate";
    }
}

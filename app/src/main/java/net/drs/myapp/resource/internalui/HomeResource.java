package net.drs.myapp.resource.internalui;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.drs.myapp.api.IUserDetails;

@Controller
public class HomeResource {

    @Autowired
    IUserDetails userDetails;

    @GetMapping(value = { "", "/", "/guest"})
    public String showHome() {
        return "welcome";
    }

    @GetMapping("/all")
    public String hello() {
        return "Hello Youtube";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/all")
    public String securedHello(Principal princi) {
        userDetails.activeteUser(new Long(123));
        return "Secured Hello";
    }

    // @PreAuthorize("hasAnyRole('USER1')")
    @GetMapping("/secured/alternate")
    public String alternate() {
        return "alternate";
    }
}

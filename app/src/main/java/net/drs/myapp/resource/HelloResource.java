package net.drs.myapp.resource;

import java.security.Principal;

import net.drs.myapp.api.IUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("/rest/hello")
//@RestController
public class HelloResource {

    @Autowired
    IUserDetails userDetails;

    @GetMapping("/all")
    public String hello() {
        return "Hello Youtube";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/all")
    public String securedHello(Principal princi) {
        return "Secured Hello";
    }

  //  @PreAuthorize("hasAnyRole('USER1')")
    @GetMapping("/secured/alternate")
    public String alternate() {
        return "alternate";
    }
}

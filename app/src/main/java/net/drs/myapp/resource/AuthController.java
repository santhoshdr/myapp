package net.drs.myapp.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.drs.myapp.config.JwtTokenProvider;
import net.drs.myapp.dto.LoginRequest;
import net.drs.myapp.repositpry.UsersRepository;

@Controller
@RequestMapping("/api/auth")
public class AuthController extends GenericService {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ModelAndView authenticateUser(LoginRequest loginRequest, HttpServletRequest httpservletRequest,RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = tokenProvider.generateToken(authentication);
                httpservletRequest.getSession(true);
                httpservletRequest.getSession().setAttribute("userloggedin", jwt);
                return new ModelAndView("redirect:/user/loginHome");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Entered UserName or Password is wrong.Please try again!");
                return new ModelAndView("redirect:/home/guest");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Entered UserName or Password is wrong.Please try again!");
            return new ModelAndView("redirect:/home/guest");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        try {

            logger.info(" This session was Created at : " + httpSession.getCreationTime());
            logger.info(" This session was Last accessed at : " + httpSession.getLastAccessedTime());
            logger.info(" Session Destroyed : " + httpSession.getId());
            httpSession.getId();
            httpSession.invalidate();
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/showData";
        }
    }

    @GetMapping("/showData")
    public String showData(LoginRequest loginRequest) {
        try {
            return "wonderful";
        } catch (Exception e) {
            return "redirect:/showData";
        }
    }
}

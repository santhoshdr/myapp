package net.drs.myapp.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.drs.myapp.config.JwtTokenProvider;
import net.drs.myapp.dto.LoginRequest;
import net.drs.myapp.repositpry.UsersRepository;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository userRepository;

    /*
     * @Autowired RoleRepository roleRepository;
     */
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ModelAndView authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            ModelAndView modelAndView = new ModelAndView("loginSuccess");
            modelAndView.addObject("login", true);
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("loginFailure");
                    modelAndView.addObject("login", false);
                    return modelAndView;
        }
    }

    /*
     * @PostMapping("/signup") public ResponseEntity<?> registerUser(@Valid
     * 
     * @RequestBody SignUpRequest signUpRequest) {
     * if(userRepository.existsByUsername(signUpRequest.getUsername())) { return
     * new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
     * HttpStatus.BAD_REQUEST); }
     * 
     * if(userRepository.existsByEmail(signUpRequest.getEmail())) { return new
     * ResponseEntity("Email Address already in use!", HttpStatus.BAD_REQUEST);
     * }
     * 
     * // Creating user's account Users user = new
     * Users(signUpRequest.getName(), signUpRequest.getUsername(),
     * signUpRequest.getEmail(), signUpRequest.getPassword());
     * 
     * user.setPassword(passwordEncoder.encode(user.getPassword()));
     * 
     * Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
     * .orElseThrow(() -> new AppException("User Role not set."));
     * 
     * user.setRoles(Collections.singleton(userRole));
     * 
     * Users result = userRepository.save(user);
     * 
     * URI location = ServletUriComponentsBuilder
     * .fromCurrentContextPath().path("/users/{username}")
     * .buildAndExpand(result.getUsername()).toUri();
     * 
     * return ResponseEntity.created(location).body(new ApiResponse(true,
     * "User registered successfully")); }
     */
}

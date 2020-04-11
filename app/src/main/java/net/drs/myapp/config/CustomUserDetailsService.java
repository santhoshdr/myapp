package net.drs.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.model.Users;
import net.drs.myapp.repositpry.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository UsersRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Let people login with either username or email
        Users user = UsersRepository.findByUsersnameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));

        if(user.getActive() == 0) {
           throw  new DisabledException("User is not active.Kindly activate theaccount by verifying the email");
        }
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Users user = new Users();
        try {
            user = UsersRepository.findById(id).orElseThrow(() -> new Exception("User"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return UserPrincipal.create(user);
    }
}
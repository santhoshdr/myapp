package net.drs.myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.dao.IRegistrationDAO;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;
import net.drs.myapp.repositpry.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository UsersRepository;

    @Autowired
    private IRegistrationDAO registrationDAO;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

             // Let people login with either username or email
            Users users = UsersRepository.findByUsersnameOrEmail(usernameOrEmail, usernameOrEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));

            if (users.getActive() == 0) {
                throw new DisabledException("User is not active.Kindly activate theaccount by verifying the email");
            }
            if (registrationDAO.checkIfUserExistsByUser_ID(users)) {
                throw new DisabledException("You need to be a member of the group to login.");
            }
            return UserPrincipal.create(users);
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
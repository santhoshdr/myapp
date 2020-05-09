package net.drs.myapp.dao;

import java.util.Set;

import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.model.CompleteUserDetails;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;

public interface IRegistrationDAO {

    // normal User
    User addUser(User user, Set<Role> roles);

    User addUserandGetUserId(User user, Set<Role> roles);
    
    boolean  checkIfUserExistsByUser_ID(Users user) ;

    boolean checkIfUserExistbyName(User user) throws Exception;

    // checks email id and is active...
    boolean checkIfUserExistbyEmailId(User user) throws Exception;
    
    
    // check just email id is present or not...
    boolean checkIfUserEmailIdExists(User user) throws Exception;
    

    boolean checkifUserExistbyPhoneNumber(User user) throws Exception;

    boolean addFotographer(Fotographer fotographer);

    boolean completeRegistration(CompleteUserDetails completeUserDetails);

    User checkIfUserEmailisPresentandVerified(String email) throws Exception;

    Users checkIfUserPhoneisPresentandVerified(String emailorphoneNumber) throws Exception;

    boolean updateUserWithTemperoryPassword(Users users);
    
    User getTemporaryActivationTokenforUser(String emailidorphonenumber) throws Exception;
    
    boolean activateUserIftemporaryPasswordMatches(User user) throws Exception;

    boolean resetPassword(Users users);

    void updateUserWithTemperoryPassword(User user) throws Exception;


}

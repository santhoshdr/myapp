package net.drs.myapp.dao;

import java.util.Set;

import net.drs.myapp.model.CompleteUserDetails;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.OtpDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;

public interface  IRegistrationDAO{

    // normal User
    User addUser(User user, Set<Role> roles);

    User addUserandGetUserId(User user, Set<Role> roles) throws Exception;
    
    Users  checkIfUserExistsByUser_ID(Users user) ;

    boolean checkIfUserExistbyName(User user) throws Exception;

    // checks email id and is active...
    Users  checkIfUserExistbyEmailId(User user) throws Exception;
    
    
    // check just email id is present or not...
    Users checkIfUserEmailIdExists(User user) throws Exception;
    

    Users checkifUserExistbyPhoneNumber(User user) throws Exception;

    boolean addFotographer(Fotographer fotographer);

    boolean completeRegistration(CompleteUserDetails completeUserDetails);

 //   User checkIfUserEmailisPresentandVerified(String email) throws Exception;
    
    Users checkIfUserEmailisPresentandVerified(String email) throws Exception;

    Users checkIfUserPhoneisPresentandVerified(String emailorphoneNumber) throws Exception;

    boolean updateUserWithTemperoryPassword(Users users);
    
    User getTemporaryActivationTokenforUser(String emailidorphonenumber) throws Exception;
    
    boolean activateUserIftemporaryPasswordMatches(Users user) throws Exception;

    boolean resetPassword(Users users);

    void updateUserWithTemperoryPassword(User user) throws Exception;
    
    
    public Users updateUserasActive(OtpDTO otpDTO ) throws Exception ;

    //boolean checkIfUserExistbyPhoneNumber(User user);


}

package net.drs.myapp.dao;

import java.util.List;

import net.drs.myapp.app.exception.RoleException;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.model.Otp;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Wed;

public interface IUserDAO {

    List<User> getAllUsers(int numberofUsers);

    List<User> getAllActiveUsers(int numberofUsers);

    List<User> getAllAdminActiveUsers(int numberofUsers);

    User getUser(Long userId);
    
    User getUser(String emailId);

    boolean isUserActive(Long userId);

    boolean activateUser(Long userId);

    boolean deactivateUser(User user);

    boolean updateUser(User user);

    // to send OTP
    boolean validateOTPGenerate(Long userid, char[] generatedOTP, int vaidFor, boolean isValidated);

    Wed createWedProfile(Wed wed);

    List<Wed> fetchWedProfile(Long loggedInUser);

    Wed updateWedProfile(Wed wed, Long Id);

    // to validate entered OTP
    // boolean validateOTPVerify(Long userid,String generatedOTP, String
    // vaidFor,boolean isValidated);

    boolean updateUserEnteredOTP(User userid, String userEnteredOTP);

    Otp getOTPForUserId(Long userid);

    boolean changeUserRole(UserServiceDTO user) throws RoleException;
    
    boolean changePassword(ResetPasswordDTO resetPassword);

}

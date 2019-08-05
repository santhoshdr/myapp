package net.drs.myapp.api;

import java.util.List;

import net.drs.myapp.app.exception.RoleException;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.model.User;

/**
 * 
 * This service operates on list of registered users...
 * 
 * @author srajanna
 *
 */

public interface IUserDetails {

    // this will get all the users
    // int numberofUser - get numberofUser - records - fetching all can create
    // performance issues
    List<UserServiceDTO> getAllUsers(int numberofUser);

    // get the information of specific user - based on user id
    User getUserById(Long userid);

    User getUserById(String emailId);

    // get all Active users
    List<UserServiceDTO> getAllActiveUsers(int numberofUser);

    List<UserServiceDTO> getAllAdminActiveUsers(int numberofUser);

    // check if the user is active or not, based on userid
    boolean isUserActive(Long userId);

    // Activate User
    boolean activeteUser(Long userId);

    // Deactivate User / Delete User
    boolean deactiveUser(UserServiceDTO userServiceDTO);

    // if the user need to update the details..
    boolean updateUserDetails(User user);

    boolean changeUserRole(UserServiceDTO userServiceDTO) throws RoleException;

    WedDTO createWedProfile(WedDTO wedDTO, Long addedBy) throws Exception;

    List<WedDTO> fetchWedProfile(Long loggedinUserId) throws Exception;

    WedDTO updateWedProfile(WedDTO wedDTO, Long addedBy) throws Exception;

    boolean changePassword(ResetPasswordDTO passwordDTO) throws Exception;
}

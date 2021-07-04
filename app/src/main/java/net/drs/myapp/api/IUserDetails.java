package net.drs.myapp.api;

import java.util.List;

import net.drs.myapp.app.exception.RoleException;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserDTO;
import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.dto.WedDTO;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;

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
    List<Users> getAllUsers(int numberofUser);

    List<User> getAllMembers(int numberofUser);
    
    List<User> getAllMembersAddedbyMe(Long loggedInUser);
    
    //List<UserServiceDTO> getAllMembers();
    
    List<User> getAllActiveMembers();

    // get the information of specific user - based on user id
    User getMemberById(Long userid);
    
    User getUserById(Long userid);
    
    User getUserById(String emailId);
    
    // from user table
    Users getUsersById(Long userid);
    

    // get all Active users
    List<UserServiceDTO> getAllActiveUsers(int numberofUser);

    List<UserServiceDTO> getAllAdminActiveUsers(int numberofUser);

    // check if the user is active or not, based on userid
    boolean isUserActive(Long userId);

    // Activate User
    boolean activeteUser(UserDTO userDTO);

    // Deactivate User / Delete User
    boolean deactiveUser(UserDTO userDTO);
    
    boolean makeorremoveAdmin(UserDTO userDTO);

    // if the user need to update the details..
    boolean updateUserDetails(User user);
    
    // use this
    boolean updateUserRole(Long userId,List<String>  roleNames,String action);

    boolean changeUserRole(UserServiceDTO userServiceDTO) throws RoleException;

    WedDTO createWedProfile(WedDTO wedDTO, Long addedBy) throws Exception;

    List<WedDTO> fetchMyWedProfiles(Long loggedinUserId,Long wedId) throws Exception;
    
    List<WedDTO> fetchWedProfile(Long loggedInUser,Long profileId,boolean canViewProfile) throws Exception;
   
    WedDTO fetchSelectedWedProfile(Long wedId) throws Exception;

    WedDTO updateWedProfile(WedDTO wedDTO, Long addedBy) throws Exception;
    
    WedDTO  deletePhoto(String photoName, String folderName,Long addedBy,Long wedId) throws Exception;

    boolean changePassword(ResetPasswordDTO passwordDTO) throws Exception;

    
    /**
     * Logged in user can add multiple members
     * @param userDTO
     * @return
     * @throws Exception
     */
    UserDTO addMember(UserDTO userDTO) throws Exception;

    WedDTO downloadFile(String fileName, Long loggedInUserId,Long wedId) throws Exception;

    boolean deleteFile(String fileName, long wedId, Long loggedInUserId) throws Exception;
}

package net.drs.myapp.api;

import java.util.List;

import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.model.User;


/**
 * 
 * This service operates on list of registered users...
 * 
 * @author srajanna
 *
 */


public interface IUserDetails  {

	
	// this will get all the users
	//int numberofUser - get  numberofUser - records - fetching all can create performance issues
	 List<UserServiceDTO> getAllUsers(int numberofUser);
	
	// get the information of specific user - based on user id
	User getUserById(Long userid);
	
	
	// get all Active users
	List<UserServiceDTO> getAllActiveUsers(int numberofUser);
	
	
	List<UserServiceDTO> getAllAdminActiveUsers(int numberofUser);
	
	//check if the user is active or not, based on userid
	boolean isUserActive(Long userId);
	
	// Activate User
	boolean activeteUser(Long userId);
	
	// Deactivate User / Delete User
	boolean deactiveUser(Long userId);
	
	
	//if the user need to update the details.. 
	boolean updateUserDetails(User user);	
}

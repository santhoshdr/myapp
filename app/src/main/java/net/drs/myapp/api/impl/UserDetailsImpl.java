package net.drs.myapp.api.impl;

import java.util.ArrayList;
import java.util.List;

import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.dao.IUserDAO;
import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.model.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("userDetails")
@Transactional
public class UserDetailsImpl implements IUserDetails {

	@Autowired
	private IUserDAO userDAO;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<UserServiceDTO> getAllUsers(int numberofUsers) {
		
		List<UserServiceDTO> userDTO = new ArrayList<UserServiceDTO>();
		
		List<User> users = userDAO.getAllUsers(numberofUsers);
		users.forEach(user -> {
			UserServiceDTO udto = new  UserServiceDTO();
			modelMapper.map(user, udto);
			userDTO.add(udto);
		});
		return userDTO;
	}

	
	// this returns user object no matter user is active or inactive
	@Override
	public User getUserById(Long userId) {
		return userDAO.getUser(userId);
	}

	@Override
	public List<UserServiceDTO> getAllActiveUsers(int numberofUsers) {
	List<UserServiceDTO> userDTO = new ArrayList<UserServiceDTO>();
		
		List<User> users = userDAO.getAllActiveUsers(numberofUsers);
		users.forEach(user -> {
			UserServiceDTO udto = new  UserServiceDTO();
			modelMapper.map(user, udto);
			userDTO.add(udto);
			
		});
		//userDAO.getAllUsers();
		return userDTO;
	}

	@Override
	public boolean isUserActive(Long userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean activeteUser(Long userId) {
		userDAO.activateUser(userId);
		return false;
	}

	@Override
	public boolean deactiveUser(Long userId) {
		return userDAO.deactivateUser(userId);
	}

	@Override
	public boolean updateUserDetails(User user) {
		return userDAO.updateUser(user);
	}


	@Override
	public List<UserServiceDTO> getAllAdminActiveUsers(int numberofUser) {
		
		List<UserServiceDTO> userDTO = new ArrayList<UserServiceDTO>();
		List<User> users = userDAO.getAllAdminActiveUsers(numberofUser);
		users.forEach(user -> {
			UserServiceDTO udto = new  UserServiceDTO();
			modelMapper.map(user, udto);
			userDTO.add(udto);
			
		});
		//userDAO.getAllUsers();
		return userDTO;
	}



}

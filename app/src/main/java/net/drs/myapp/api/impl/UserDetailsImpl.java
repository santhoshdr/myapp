package net.drs.myapp.api.impl;

import java.util.List;

import net.drs.myapp.api.IUserDetails;
import net.drs.myapp.dao.IUserDAO;
import net.drs.myapp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("userDetails")
@Transactional
public class UserDetailsImpl implements IUserDetails {

	@Autowired
	private IUserDAO userDAO;
	
	
	@Override
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	
	// this returns user object no matter user is active or inactive
	@Override
	public User getUserById(Long userId) {
		return userDAO.getUser(userId);
	}

	@Override
	public List<User> getAllActiveUsers() {
		return userDAO.getAllActiveUsers();
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



}

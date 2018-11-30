package net.drs.myapp.dao;

import java.util.Set;

import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;

public interface IRegistrationDAO {

	// normal User
	boolean addUser(User user,Set<Role> roles) ;
	
	boolean checkIfUserExistbyName(User user) throws Exception;
	
	boolean checkIfUserExistbyEmailId(User user) throws Exception;
	
	boolean checkifUserExistbyPhoneNumber(User user);
	
	boolean addFotographer(Fotographer fotographer);

	
}


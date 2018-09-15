package net.drs.myapp.dao;

import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.User;

public interface IRegistrationDAO {

	// normal User
	boolean addUser(User user) ;
	
	boolean checkIfUserExistbyName(User user) throws Exception;
	
	boolean checkIfUserExistbyEmailId(User user);
	
	boolean checkifUserExistbyPhoneNumber(User user);
	
	boolean addFotographer(Fotographer fotographer);

	
}


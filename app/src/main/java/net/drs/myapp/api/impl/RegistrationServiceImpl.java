package net.drs.myapp.api.impl;

import net.drs.myapp.api.IRegistrationService;
import net.drs.myapp.dao.IRegistrationDAO;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("registrationService")
@Transactional
public class RegistrationServiceImpl implements IRegistrationService {

	
	@Autowired
	private IRegistrationDAO registrationDAO;
	
	@Override
	public boolean adduser(User user) {
		
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return registrationDAO.addUser(user);	
	}

	@Override
	public boolean addFotographer(Fotographer fotographer) {
		registrationDAO.addFotographer(fotographer);
		return true;
	}

}

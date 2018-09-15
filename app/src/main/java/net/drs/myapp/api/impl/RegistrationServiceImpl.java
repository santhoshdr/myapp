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
	public boolean adduser(User user) throws Exception {
		
		try {
			boolean result = registrationDAO.checkIfUserExistbyName(user);
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()))	;
			if(!result){
				return registrationDAO.addUser(user);	
			}else{
				throw new Exception("Some problem.");	
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		
		
	}

	@Override
	public boolean addFotographer(Fotographer fotographer) {
		registrationDAO.addFotographer(fotographer);
		return true;
	}

}

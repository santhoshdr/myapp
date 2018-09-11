package net.drs.myapp.api.impl;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.drs.myapp.dao.IRegistrationDAO;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository("registrationDAO")
@Transactional
public class RegistrationDAOImpl  implements  IRegistrationDAO{

	@PersistenceContext	
	private EntityManager entityManager;
	
	public boolean addUser(User user) {
		try{
	//		entityManager.persist(user);
			Users users = new Users(); 
			Set<Role> roles = new HashSet();
			Role role = new Role();
			role.setRole("ADMIN");
			//role.setRoleId(123);
			roles.add(role);
			
		//	entityManager.merge(role);
			
			
		//	users.setId(user.getUserId());
			users.setEmail(user.getEmailAddress());
			users.setActive(1);
			users.setRoles(roles);
			users.setName(user.getFirstName());
			users.setPassword(user.getPassword());
			//users.set
			
			
			entityManager.merge(users);
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	
	
	public boolean addFotographer(Fotographer fotographer) {
		entityManager.persist(fotographer);
		return true;
	}

		
}

package net.drs.myapp.api.impl;

import java.util.HashSet;
import java.util.List;
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
	
	public boolean addUser(User user,Set<Role> roles) {
		try{
			
			Users users = new Users(); 
/*			Set<Role> roles = new HashSet();
			Role role = new Role();
			role.setRole("ADMIN");*/
			//roles.add(role);
		//	entityManager.merge(role);
		//	users.setId(user.getUserId());
			users.setEmail(user.getEmailAddress());
			users.setActive(1);
			users.setRoles(roles);
			users.setName(user.getFirstName());
			users.setPassword(user.getPassword());
			//users.set
			
			entityManager.persist(users);
			user.setUserId(users.getId());
			entityManager.merge(user);
			
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



	@Override
	public boolean checkIfUserExistbyName(User user) throws Exception {
		try{
			List list = entityManager.createQuery("SELECT count(*) FROM User WHERE firstName=?1 and isActive='true'").setParameter(1,user.getFirstName()).getResultList();;
			if(list.get(0)!=null && ((Long)list.get(0)).intValue()>0) {
				throw new Exception("UserName Already present. Try with different username");
			}
		}catch(Exception e ){
			throw e;
		}
		return false;
	}



	@Override
	public boolean checkIfUserExistbyEmailId(User user) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean checkifUserExistbyPhoneNumber(User user) {
		// TODO Auto-generated method stub
		return false;
	}

		
}

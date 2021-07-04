package net.drs.myapp.api.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dao.IRegistrationDAO;
import net.drs.myapp.model.CompleteUserDetails;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.OtpDTO;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;
import net.drs.myapp.utils.AppUtils;

@Repository("registrationDAO")
@Transactional
public class RegistrationDAOImpl implements IRegistrationDAO {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public User addUser(User user, Set<Role> roles) {
        try {
            Users users = new Users();
            
            users.setEmail(user.getEmailAddress());
            users.setActive(1);
            users.setRoles(roles);
            users.setName(user.getFirstName());
            users.setPassword(user.getPassword());
            users.setLastName(user.getLastName());
            entityManager.persist(users);
            
            
            // this needs to be dealt in better way
            if(user.getEmailAddress().equalsIgnoreCase("admin@admin.com")) {
            	user.setActive(true); // by default
            }
            user.setUserId(users.getId());
            return entityManager.merge(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addFotographer(Fotographer fotographer) {
        entityManager.persist(fotographer);
        return true;
    }

    @Override
    public boolean checkIfUserExistbyName(User user) throws Exception {
        try {
            List list = entityManager.createQuery("SELECT count(*) FROM User WHERE firstName=?1 and isActive='true'").setParameter(1, user.getFirstName()).getResultList();
            ;
            if (list.get(0) != null && ((Long) list.get(0)).intValue() > 0) {
                throw new Exception("UserName Already present. Try with different username");
            }
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    @Override
    public Users checkIfUserExistbyEmailId(User user) throws Exception {
            // and isActive='1'
            List list = entityManager.createQuery("from  Users WHERE email=?1  ").setParameter(1, user.getEmailAddress()).getResultList();
            if(list.size() == 1) {
                return (Users)list.get(0);
            }
            return null;
    }
    

    // need to change below method
    @Override
    public Users checkifUserExistbyPhoneNumber(User user) throws Exception {
        try {
            List userList = entityManager.createQuery("from  Users WHERE phonenumber=?1").setParameter(1, user.getMobileNumber()).getResultList();
            if (userList.size() == 1) {
                return (Users) userList.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    @Override
    public boolean completeRegistration(CompleteUserDetails completeUserDetails) {
        try {
            entityManager.merge(completeUserDetails);
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    
    // verified 
    @Override
    public Users checkIfUserEmailisPresentandVerified(String email) throws Exception {
        /*
         * List<DepartmentEntity> depts =
         * manager.createQuery("Select a From DepartmentEntity a",
         * DepartmentEntity.class).getResultList();
         */
        List list = entityManager.createQuery("SELECT user FROM Users user WHERE email=?1", Users.class).setParameter(1, email).getResultList();
        // need to check size first - else arry index out of bound exception
        // will occur
        if (list.size() == 1 && list.get(0) != null ) {
            return ((Users) list.get(0));
        } else {
            throw new Exception("Unable to find the user. Contact administrator");
        }
    }
    
    
    
//    @Override
//    public User checkIfUserEmailisPresentandVerified(String email) throws Exception {
//        /*
//         * List<DepartmentEntity> depts =
//         * manager.createQuery("Select a From DepartmentEntity a",
//         * DepartmentEntity.class).getResultList();
//         */
//        List list = entityManager.createQuery("SELECT user FROM Users user WHERE email=?1", User.class).setParameter(1, email).getResultList();
//        // need to check size first - else arry index out of bound exception
//        // will occur
//        if (list.size() == 1 && list.get(0) != null ) {
//            return ((User) list.get(0));
//        } else {
//            throw new Exception("Unable to find the user. Contact administrator");
//        }
//    }

    @Override
    public Users checkIfUserPhoneisPresentandVerified(String phoneNumberoremailid) throws Exception {
        Users users = null;
        users = (Users) entityManager.createQuery("from Users WHERE EMAIL=:email").setParameter("email", phoneNumberoremailid).getSingleResult();
        if (users == null) {
            throw new Exception("Provided Email Id does not exist. Please provide valid email id");
        }
        if (users.getActive() != 1) {
            throw new Exception("Your account is not active. Please activate by clicking activate link ");
        }
        return users;
    }

    @Override
    public Users addUserandGetUserId(User user, Set<Role> roles) throws Exception {
        Users users = new Users();
        try {
            users.setEmail(user.getEmailAddress());
            // setting Active as 0. Once email is verified, the active will get
            // updated to 1. It means, since then
            // the account is active.
            users.setActive(0);
            users.setRoles(roles);
            users.setName(user.getFirstName());
            users.setPassword(user.getPassword());
            users.setLastName(user.getLastName());
            users.setPhonenumber(user.getMobileNumber());
            entityManager.persist(users);
            return users;
          
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Exception Occurred while storing the data. Please try after some time.");

        }
    }

    // new
    @Override
    public void updateUserWithTemperoryPassword(User user) throws Exception {

        try {
            User storedUser = entityManager.find(User.class, user.getId());
            storedUser.setTemporaryActivationString(user.getTemporaryActivationString());
            storedUser.setTemporaryActivationSentDate(System.currentTimeMillis());
            storedUser.setTemporaryActivationvalidforInMinutes(ApplicationConstants.TEMPARORY_ACTIVATION_EXPIRY_DURATION);
            entityManager.persist(storedUser);

            
            // forgot password scenario - using getUserID
            Users users = entityManager.find(Users.class, user.getUserId());
            users.setPassword(ApplicationConstants.PASSWORD_RESETTED);
            entityManager.persist(users);

        } catch (Exception e) {
            throw new Exception("Unable to save Temparory Password. Please contact Administrator");
        }
        /*
         * int result = entityManager.
         * createQuery("update User set temporaryActivationSentDate =:tempPassword WHERE emailAddress=:emailid and id=:userId"
         * ) .setParameter("tempPassword", users.getT) .setParameter("emailid",
         * users.getEmail()).setParameter("userId",
         * users.getId()).executeUpdate(); if (result == 1) { return true; }
         */
    }

    // old
    @Override
    public boolean updateUserWithTemperoryPassword(Users users) {
        int result = entityManager.createQuery("update Users set password =:tempPassword ,  isTempPassord=1 WHERE email=:emailid and USER_Id=:userId")
                .setParameter("tempPassword", users.getPassword())
                .setParameter("emailid", users.getEmail())
                .setParameter("userId", users.getId())
                .executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean resetPassword(Users users) {

        int result = entityManager.createQuery("update Users set password =:newPassword WHERE email=:emailid and USER_Id=:userId").setParameter("newPassword", users.getPassword())
                .setParameter("emailid", users.getEmail()).setParameter("userId", users.getId()).executeUpdate();
        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public User getTemporaryActivationTokenforUser(String emailidorphonenumber) throws Exception {
        return (User) entityManager.createQuery("from User  WHERE emailAddress=:emailid").setParameter("emailid", emailidorphonenumber).getSingleResult();
    }

    @Override
    public Users updateUserasActive(OtpDTO otpDTO) throws Exception {

        Users storedUser = (Users) entityManager.createQuery("from Users  WHERE  phonenumber=:phonenumber").setParameter("phonenumber", otpDTO.getPhoneNumber()).getSingleResult();
        storedUser.setPassword(AppUtils.encryptPassword(otpDTO.getPassword()));
        storedUser.setActive(1);
        return entityManager.merge(storedUser);
    }

    @Override
    public boolean activateUserIftemporaryPasswordMatches(Users user) throws Exception {

//        User storedUser = entityManager.find(User.class, user.getId());
//        storedUser.setAccountValidTill(user.getAccountValidTill());
//        storedUser.setActive(true);
//        entityManager.merge(storedUser);
        Users users = entityManager.find(Users.class, user.getId());
        users.setPassword(AppUtils.encryptPassword(user.getPassword()));
        users.setActive(1);
        entityManager.persist(users);
        return true;
    }

    @Override
    public Users checkIfUserEmailIdExists(User user) throws Exception {
        try {
        	return (Users) entityManager.createQuery("from Users  WHERE  email=:email").setParameter("email", user.getEmailAddress()).getSingleResult();
        	            //List list = entityManager.createQuery("SELECT count(*) FROM User WHERE emailAddress=?1").setParameter(1, user.getEmailAddress()).getResultList();
            } catch (Exception e) {
            throw e;
        }
        
    }

    
    // check user exists and is active
    @Override
    public Users checkIfUserExistsByUser_ID(Users users) {
        return  (Users) entityManager.createQuery("FROM Users WHERE id=?1").
        		setParameter(1, users.getId()).getSingleResult();
    }

	@Override
	public User addUserandGetUserId1(User user) throws Exception {
		  entityManager.persist(user);
          return user;
	}

    /*
     * @Override public boolean checkIfUserExistbyPhoneNumber(User user) { try {
     * List list = entityManager.
     * createQuery("SELECT count(*) FROM User WHERE mobileNumber=?1").
     * setParameter(1, user.getMobileNumber()).getResultList(); if (list.get(0)
     * != null && ((Long) list.get(0)).intValue() > 0) { return true; } } catch
     * (Exception e) { throw e; } return false; }
     */
}

package net.drs.myapp.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.app.exception.RoleException;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.dao.IUserDAO;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.dto.UserServiceDTO;
import net.drs.myapp.model.Otp;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;
import net.drs.myapp.model.Wed;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements IUserDAO {

    /*
     * @Autowired private SessionFactory sessionFactory;
     */

    @PersistenceContext
    private EntityManager entityManager;

    public List<Users> getAllUsers(int numberofUsers) {
        return entityManager.createQuery("from Users").getResultList();
    }

    public List<User> getAllActiveUsers(int numberofUsers) {
        String selectquery = "from User where isActive= :active";
        javax.persistence.Query query = entityManager.createQuery(selectquery);
        query.setParameter("active", true);
        return query.getResultList();
    }

    /*
     * public User getUser(Long userId){ // not working for 1 - need to check
     * return (User) sessionFactory.getCurrentSession().get(User.class, userId);
     * }
     * 
     * public boolean isUserActive(Long userId) {
     * 
     * String selectQuery = "FROM User WHERE userId = :userId"; return (Boolean)
     * sessionFactory.getCurrentSession() .createQuery(selectQuery)
     * .setParameter("userId", userId) .getSingleResult();
     * 
     * }
     * 
     * public boolean activateUser(Long userId) { String selectQuery =
     * "update User set isActive = :isActive WHERE userId = :userId"; return
     * sessionFactory.getCurrentSession() .createQuery(selectQuery)
     * .setParameter("userId", userId) .setParameter("isActive", true)
     * .executeUpdate() >= 1 ? true : false;
     * 
     * }
     * 
     * public boolean deactivateUser(Long userId) { String selectQuery =
     * "update User set isActive = :isActive WHERE userId = :userId"; return
     * sessionFactory.getCurrentSession() .createQuery(selectQuery)
     * .setParameter("userId", userId) .setParameter("isActive", false)
     * .executeUpdate() >= 1 ? true : false;
     * 
     * }
     * 
     * public boolean updateUser(User user) { // update the user and send back
     * updated user object sessionFactory.getCurrentSession().update(user);
     * return true;
     * 
     * }
     * 
     * 
     * public boolean validateOTPGenerate(Long userid, char[] generatedOTP, int
     * vaidFor, boolean isValidated) {
     * 
     * Otp otp = new Otp(); otp.setUserId(userid);
     * otp.setUniqueOTPSent(generatedOTP); otp.setOtpValidFor(vaidFor);
     * otp.setIsvalidated(false);
     * 
     * sessionFactory.getCurrentSession().persist(otp); return true; }
     * 
     * public boolean validateOTPVerify(Long userid, String generatedOTP, String
     * vaidFor, boolean isValidated) { // TODO Auto-generated method stub return
     * false; }
     * 
     * public Otp getOTPForUserId(Long userid) {
     * 
     * String otpQuery ="FROM Otp where userid = :userid"; Otp otp =
     * (Otp)sessionFactory.getCurrentSession() .createQuery(otpQuery)
     * .setParameter("userid", userid).getSingleResult(); return otp; }
     * 
     * public boolean updateUserEnteredOTP(User user, String userEnteredOTP) {
     * 
     * String otpQuery
     * ="update Otp set userEnteredOTP = :userEnteredOTP where userid = :userid"
     * ; return sessionFactory.getCurrentSession() .createQuery(otpQuery)
     * .setParameter("userid", user.getUserId()) .setParameter("userEnteredOTP",
     * userEnteredOTP.toCharArray()).executeUpdate()> 0 ?true : false;
     * 
     * }
     */

    
    // viewMember flow... id is used
    // in view my profile = userId is used..
    @Override
    public User getUser(Long userId) {
        return (User) entityManager.createQuery("from User where userId=:userId").setParameter("userId", userId).getSingleResult();
    }
    
    @Override
    public User getMemberByID(Long userId) {
        return (User) entityManager.createQuery("from User where id=:id").setParameter("id", userId).getSingleResult();
    }
    

    @Override
    public boolean isUserActive(Long userId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean activateUser(User user) {
        User userfromdDB = entityManager.find(User.class, user.getUserId());
        userfromdDB.setActive(user.isActive());
        userfromdDB.setUpdatedBy(user.getUpdatedBy());
        userfromdDB.setUpdatedDate(user.getUpdatedDate());
        entityManager.persist(userfromdDB);
        return true;
    }

    @Override
    public boolean deactivateUser(User user) {

        User userfromdDB = entityManager.find(User.class, user.getUserId());
        userfromdDB.setActive(user.isActive());
        userfromdDB.setUpdatedBy(user.getUpdatedBy());
        userfromdDB.setUpdatedDate(user.getUpdatedDate());
        entityManager.persist(userfromdDB);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        User userfromdDB = entityManager.find(User.class, user.getId());
        userfromdDB.setAddress(user.getAddress());
        userfromdDB.setFirstName(user.getFirstName());
        userfromdDB.setLastName(user.getLastName());
        userfromdDB.setMobileNumber(user.getMobileNumber());
        entityManager.persist(userfromdDB);
        entityManager.close();
        return true;
    }

    @Override
    public boolean validateOTPGenerate(Long userid, char[] generatedOTP, int vaidFor, boolean isValidated) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateUserEnteredOTP(User userid, String userEnteredOTP) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Otp getOTPForUserId(Long userid) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> getAllAdminActiveUsers(int numberofUsers) {
        // String selectquery="from User where isActive= :active";
        String selectquery = "from Users us , User u where us.id=u.userId and u.isActive='true'";// and
                                                                                                 // us.role='ADMIN'";

        javax.persistence.Query query = entityManager.createQuery(selectquery);
        // query.setParameter("active", true);
        List<User> userList = new ArrayList<User>();
        List tempList = new ArrayList();
        tempList = query.getResultList();

        Object[] items = tempList.toArray();

        for (Object item : items) {
            Object[] arr = (Object[]) item;
            Users users = (Users) arr[0];
            User user = (User) arr[1];

            Set<Role> role = users.getRoles();

            Iterator it = role.iterator();

            while (it.hasNext()) {
                Role actualRole = (Role) it.next();

                if (actualRole.getRole() != null && actualRole.getRole().equalsIgnoreCase(ApplicationConstants.ROLE_ADMIN)) {
                    user.setRoles(users.getRoles());
                    userList.add(user);
                } else {
                    System.out.println("User is not ADMIN");
                }
            }
        }

        return userList;

    }

    @Override
    public boolean changeUserRole(UserServiceDTO userServiceDTO) throws RoleException {

        try {
            Role role = null;
            Role userUpdatedRole = null;
            Users usertemp = entityManager.find(Users.class, userServiceDTO.getUserId());
            if (userServiceDTO.getRoles().iterator().hasNext()) {
                userUpdatedRole = userServiceDTO.getRoles().iterator().next();
            }
            Iterator it = usertemp.getRoles().iterator();
            while (it.hasNext()) {
                role = (Role) it.next();
                entityManager.createQuery("update Role set role =?1 where roleId = ?2 ").setParameter(1, userUpdatedRole.getRole()).setParameter(2, role.getRoleId()).executeUpdate();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RoleException("Unable to add Role. Please contact Administrator ");
        }
        return false;
    }

    @Override
    public Wed createWedProfile(Wed wed) {
        entityManager.persist(wed);
        entityManager.close();
        return wed;
    }

    @Override
    public List<Wed> fetchWedProfile(Long loggedInUser,Long wedId) {
        List<Wed> wedList = new ArrayList<>();
        if(wedId!= null) {
            wedList = (List<Wed>) entityManager.createQuery("from Wed where id=:wedId and addedBy=:addedBy").
                    setParameter("wedId", wedId).
                    setParameter("addedBy", loggedInUser)
                    .getResultList();
        }else {        
            wedList = (List<Wed>) entityManager.createQuery("from Wed where addedBy=:addedBy").
                                            setParameter("addedBy", loggedInUser)
                                            .getResultList();
        }
        return wedList;
    }

    @Override
    public Wed updateWedProfile(Wed wed) {
        
        Wed storedWed = entityManager.find(Wed.class, wed.getId());
        wed.setCreatedBy(storedWed.getCreatedBy());
        wed.setCreatedDate(storedWed.getCreatedDate());
        wed.setFolderId(storedWed.getFolderId());
        entityManager.merge(wed);
        return wed;
    }

    @Override
    public User getUser(String emailId) {
        return (User) entityManager.createQuery("from User where emailAddress=:emailId").setParameter("emailId", emailId).getSingleResult();
    }

    @Override
    public boolean changePassword(ResetPasswordDTO resetPassword) {
        Users user = entityManager.find(Users.class, resetPassword.getUserId());
        user.setPassword(resetPassword.getEncryptedPassword());
        entityManager.persist(user);
        entityManager.close();
        return true;
    }

    @Override
    public User addMember(User user) {
        return entityManager.merge(user);
    }

    // whose is_Active=1
    @Override
    public List<User> getAllActiveMembers() {
        return entityManager.createQuery("from User where isActive=true").getResultList();
    }

    @Override
    public List<User> getAllMembers() {
        return entityManager.createQuery("from User").getResultList();
    }  

    @Override
    public boolean makeorremoveAdmin(User user) {
        Users storedUser = entityManager.find(Users.class, user.getUserId());
        Role role = null; 
        boolean isadmin = false;
        Iterator it = storedUser.getRoles().iterator();
        while(it.hasNext()) {
            role = (Role)it.next();
           if(role.getRole().equalsIgnoreCase(ApplicationConstants.ROLE_ADMIN) ){
               storedUser.getRoles().remove(role);
               isadmin=true;
               break;
           }
        }
        if(isadmin) {
            entityManager.persist(storedUser);
        }else {
            Role  newrole = new Role();
            newrole .setRole(ApplicationConstants.ROLE_ADMIN);
            storedUser.getRoles().add(newrole);
            entityManager.persist(storedUser);  
        }
        return true;
    }

    @Override
    public Users getUserById(Long userid) {
        return  entityManager.find(Users.class, userid);
    }

    @Override
    public void updateUser(Users users) {
        entityManager.persist(users);
    }

    @Override
    public Wed fetchSelectedWedProfile(Long wedId) {
        return entityManager.find(Wed.class, wedId);
    }

	@Override
	public List<User> getAllMembersAddedByMe(long loggedInUser) {
		return entityManager.createQuery("from User where memberAddedBy=:loggedInUser")
				.setParameter("loggedInUser", loggedInUser)
				.getResultList();	}
}
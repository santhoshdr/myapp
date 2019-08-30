package net.drs.myapp.api.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.drs.myapp.dao.IRegistrationDAO;
import net.drs.myapp.dto.ResetPasswordDTO;
import net.drs.myapp.model.CompleteUserDetails;
import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.Role;
import net.drs.myapp.model.User;
import net.drs.myapp.model.Users;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("registrationDAO")
@Transactional
public class RegistrationDAOImpl implements IRegistrationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean addUser(User user, Set<Role> roles) {
        try {
            Users users = new Users();
            users.setEmail(user.getEmailAddress());
            users.setActive(1);
            users.setRoles(roles);
            users.setName(user.getFirstName());
            users.setPassword(user.getPassword());
            users.setLastName(user.getLastName());
            entityManager.persist(users);
            user.setUserId(users.getId());
            entityManager.merge(user);
            return true;
        } catch (Exception e) {
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
    public boolean checkIfUserExistbyEmailId(User user) throws Exception {
        try {
            // and isActive='1'
            List list = entityManager.createQuery("SELECT count(*) FROM User WHERE emailAddress=?1 ").setParameter(1, user.getEmailAddress()).getResultList();
            if (list.get(0) != null && ((Long) list.get(0)).intValue() > 0) {
                throw new Exception("UserName Already present. Try with different username");
            }
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    @Override
    public boolean checkifUserExistbyPhoneNumber(User user) throws Exception {
        try {
            List list = entityManager.createQuery("SELECT count(*) FROM User WHERE emailAddress=?1 and isActive='true'").setParameter(1, user.getEmailAddress()).getResultList();
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
    public boolean completeRegistration(CompleteUserDetails completeUserDetails) {
        try {
            entityManager.merge(completeUserDetails);
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    @Override
    public User checkIfUserEmailisPresentandVerified(String email) {
        boolean verifiedUser = false;
        User user = null;
        try {

            /*
             * List<DepartmentEntity> depts =
             * manager.createQuery("Select a From DepartmentEntity a",
             * DepartmentEntity.class).getResultList();
             */
            List list = entityManager.createQuery("SELECT user FROM User user WHERE emailAddress=?1 and isActive='true'", User.class).setParameter(1, email).getResultList();
            // need to check size first - else arry index out of bound exception
            // will occur
            if (list.size() == 1 && list.get(0) != null) {
                user = ((User) list.get(0));
                verifiedUser = true;
            }
        } catch (Exception e) {
            verifiedUser = true;
            throw e;
        }
        return user;
    }

    @Override
    public Users checkIfUserPhoneisPresentandVerified(String phoneNumberoremailid) throws Exception {
        Users users = null;
        try {
            users = (Users) entityManager.createQuery("from Users WHERE EMAIL=:email and ACTIVE='1'").setParameter("email", phoneNumberoremailid).getSingleResult();
        } catch (Exception e) {
            throw new Exception("Exception in fetching details for the user ");
        }
        return users;
    }

    @Override
    public Long addUserandGetUserId(User user, Set<Role> roles) {

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
            // String in User Table
            entityManager.persist(users);
            user.setUserId(users.getId());
            // Storing in userdetail table
            entityManager.merge(user);
        } catch (Exception e) {
            // throw exception
            e.printStackTrace();
        }
        return users.getId();
    }

    @Override
    public boolean updateUserWithTemperoryPassword(Users users) {
        int result = entityManager.createQuery("update Users set password =:tempPassword WHERE email=:emailid and USER_Id=:userId").setParameter("tempPassword", users.getPassword())
                .setParameter("emailid", users.getEmail()).setParameter("userId", users.getId()).executeUpdate();
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
    public boolean activateUserIftemporaryPasswordMatches(User user) {

        User storedUser = entityManager.find(User.class, user.getId());
        storedUser.setAccountValidTill(user.getAccountValidTill());
        storedUser.setActive(true);
        entityManager.persist(storedUser);
        return true;
    }

    @Override
    public boolean checkIfUserEmailIdExists(User user) throws Exception {
        try {
            List list = entityManager.createQuery("SELECT count(*) FROM User WHERE emailAddress=?1").setParameter(1, user.getEmailAddress()).getResultList();
            if (list.get(0) != null && ((Long) list.get(0)).intValue() > 0) {
                return true;
            }
        } catch (Exception e) {
            throw e;
        }
        return false;
    }
}

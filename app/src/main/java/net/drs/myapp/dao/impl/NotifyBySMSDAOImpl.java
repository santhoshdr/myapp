package net.drs.myapp.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.dao.INotifyBySMSDAO;
import net.drs.myapp.model.Otp;
import net.drs.myapp.utils.AppUtils;


@Repository("notficationSMSDAO")
@Transactional
public class NotifyBySMSDAOImpl implements INotifyBySMSDAO {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Otp insertEmailDatailstoDB(Otp otp) {
        
        entityManager.createQuery("update Otp set userOtpEnteredTimeStamp =:userOtpEnteredTimeStamp where phoneNumber=:phoneNumber and userOtpEnteredTimeStamp is   NULL" )
        .setParameter("userOtpEnteredTimeStamp", AppUtils.getCurrentTimeStamp())
        .setParameter("phoneNumber", otp.getPhoneNumber())
        .executeUpdate();
        
       return  entityManager.merge(otp);
    }

    @Override
    public Otp verifyEnteredOTP(String phoneNumber) {
        return (Otp) entityManager.createQuery("from Otp where phoneNumber=:phoneNumber and userOtpEnteredTimeStamp is   NULL")
        .setParameter("phoneNumber", phoneNumber).getSingleResult();
    }

    @Override
    public void  markPhoneNumberValidated(Otp otp) {
        entityManager.persist(otp);
    }

}

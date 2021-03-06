package net.drs.myapp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.dao.INotifyByEmailDAO;
import net.drs.myapp.notification.Email;
import net.drs.myapp.notification.SMS;

@Repository("notficationEmailDAO")
@Transactional
public class NotifyByEmailDAOImpl implements INotifyByEmailDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long insertEmailDatailstoDB(Email email) {
        try {
            entityManager.persist(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return email.getId();
    }

    @Override
    public List<Email> getEntriesTowhichEmailNeedstobeSent() {

        List listofEntriestowhichEmailtobeSent = new ArrayList();
        try {
            String selectquery = "from Email where needtoSendEmail= :true";
            javax.persistence.Query query = entityManager.createQuery(selectquery);
            query.setParameter("true", true);
            listofEntriestowhichEmailtobeSent = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listofEntriestowhichEmailtobeSent;

    }

    @Override
    public SMS insertEmailDatailstoDB(SMS sms) {
        return entityManager.merge(sms);    }

}

package net.drs.myapp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.drs.myapp.dao.INotifyByEmailDAO;
import net.drs.myapp.model.Email;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("notficationEmailDAO")
@Transactional
public class NotifyByEmailDAOImpl implements INotifyByEmailDAO {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public boolean insertEmailDatailstoDB(Email email) {
		
	try{
			entityManager.persist(email);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Email> getEntriesTowhichEmailNeedstobeSent() {
	
		List listofEntriestowhichEmailtobeSent = new ArrayList();
		try{
			String selectquery="from Email where needtoSendEmail= :true";
			javax.persistence.Query query = entityManager.createQuery(selectquery);
			query.setParameter("true", true);
			listofEntriestowhichEmailtobeSent =  query.getResultList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return listofEntriestowhichEmailtobeSent;
			
	}

}

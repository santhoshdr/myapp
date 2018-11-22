package net.drs.myapp.api.impl;

import java.util.List;

import net.drs.myapp.api.INotifyByEmail;
import net.drs.myapp.dao.INotifyByEmailDAO;
import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.model.Email;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository("notificationByEmailService")
@Transactional
public class NotifyByEmailImpl implements INotifyByEmail {

	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private INotifyByEmailDAO notiByEmailDAO;
	
	@Override
	public boolean insertDatatoDBforNotification(EmailDTO emailDTO) {
		// TODO Auto-generated method stub
		
		Email email = new Email();
		modelMapper.map(emailDTO, email);
		notiByEmailDAO.insertEmailDatailstoDB(email);
		
		return false;
	}


	@Override
	public boolean sendNotofication() {
		
		
		List<Email> listofUsersTowhichEmailtobesent = notiByEmailDAO.getEntriesTowhichEmailNeedstobeSent();
		
		

		
		
		
		
		return false;
	}

}

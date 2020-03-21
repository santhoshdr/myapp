package net.drs.myapp.api.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.api.INotifyByEmail;
import net.drs.myapp.dao.INotifyByEmailDAO;
import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.dto.SMSDTO;
import net.drs.myapp.model.Email;
import net.drs.myapp.model.SMS;

@Repository("notificationByEmailService")
@Transactional
public class NotifyByEmailImpl implements INotifyByEmail {

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private INotifyByEmailDAO notiByEmailDAO;

    @Override
    public Long insertDatatoDBforNotification(EmailDTO emailDTO) {
        Email email = new Email();
        modelMapper.map(emailDTO, email);
        return notiByEmailDAO.insertEmailDatailstoDB(email);
    }

    @Override
    public boolean sendNotofication() {

        List<Email> listofUsersTowhichEmailtobesent = notiByEmailDAO.getEntriesTowhichEmailNeedstobeSent();

        return false;
    }

    @Override
    public boolean updateNotificationafterSending() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public SMSDTO insertDatatoDBforNotification(SMSDTO smsDTO) {
        SMS sms = new SMS();
        modelMapper.map(smsDTO, sms);
        sms = notiByEmailDAO.insertEmailDatailstoDB(sms);;
        modelMapper.map(sms, smsDTO);
        return smsDTO;
    }

}

package net.drs.myapp.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.drs.myapp.api.INotifyBySMS;
import net.drs.myapp.dao.INotifyBySMSDAO;
import net.drs.myapp.dto.SMSDTO;
import net.drs.myapp.model.Otp;


@Repository("notificationBySMSService")
@Transactional
public class NotifyBySMSImpl  implements INotifyBySMS{

    @Autowired
    private INotifyBySMSDAO notifyBySMSDAO;
  
    
    @Override
    public SMSDTO sendPhoneNumberVerificationSMS(SMSDTO smsdto) {
        smsdto.setReferenceId("referenceId");
        return smsdto;
    }

    @Override
    public Otp insertOTP(Otp otp) {
        return notifyBySMSDAO.insertEmailDatailstoDB(otp);
    }

}

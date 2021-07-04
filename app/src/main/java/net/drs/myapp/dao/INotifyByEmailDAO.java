package net.drs.myapp.dao;

import java.util.List;

import net.drs.myapp.notification.Email;
import net.drs.myapp.notification.SMS;

public interface INotifyByEmailDAO {

    public Long insertEmailDatailstoDB(Email email);

    public List getEntriesTowhichEmailNeedstobeSent();
    
    public SMS insertEmailDatailstoDB(SMS sms);

}

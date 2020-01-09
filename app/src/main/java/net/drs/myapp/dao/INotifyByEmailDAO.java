package net.drs.myapp.dao;

import java.util.List;

import net.drs.myapp.model.Email;
import net.drs.myapp.model.SMS;

public interface INotifyByEmailDAO {

    public Long insertEmailDatailstoDB(Email email);

    public List getEntriesTowhichEmailNeedstobeSent();
    
    public SMS insertEmailDatailstoDB(SMS sms);

}

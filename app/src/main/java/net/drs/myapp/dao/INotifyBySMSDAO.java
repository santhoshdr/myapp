package net.drs.myapp.dao;

import net.drs.myapp.model.Otp;

public interface INotifyBySMSDAO {

    public Otp insertEmailDatailstoDB(Otp  otp);
    
    public Otp verifyEnteredOTP(String phoneNumber);
    
    public void   markPhoneNumberValidated(Otp otp);
    
}

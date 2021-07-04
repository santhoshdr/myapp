package net.drs.myapp.api;

import net.drs.myapp.dto.SMSDTO;
import net.drs.myapp.model.Otp;

public interface INotifyBySMS {
    
    
    public Otp insertOTP(Otp otp);

    public SMSDTO sendPhoneNumberVerificationSMS(SMSDTO smsdto);
    
    
    
    
    
}

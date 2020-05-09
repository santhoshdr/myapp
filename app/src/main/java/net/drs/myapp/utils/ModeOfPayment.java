package net.drs.myapp.utils;

public enum ModeOfPayment {

    NETBANKING("Online/Netbanking"),
    OFFLINE("CASH/DD/MONEY ORDER");
    
    
    private String modeOfPayment;
    
    ModeOfPayment(String modeofpayment){
        this.modeOfPayment=modeofpayment;
    }
    
    public String getModeOfPayment() {
        return modeOfPayment;
    }
    
}

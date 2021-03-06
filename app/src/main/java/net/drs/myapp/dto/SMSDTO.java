package net.drs.myapp.dto;

public class SMSDTO extends CommonNotifyDTO {

    private Long Id;
    
    private Long userID;

    private  String referenceId;
    
    private String phoneNumber;
    
    
    private char[] otp;

    private String smsMessage;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setSmsSent(boolean isSmsSent) {
        this.isSmsSent = isSmsSent;
    }

    private boolean isSmsSent;

    public SMSDTO(Long userID, String phoneNumber, String message) {
        super();
        this.smsMessage = message;
        this.userID = userID;
        this.phoneNumber = phoneNumber;
    }

    public SMSDTO() {
        // TODO Auto-generated constructor stub
    }

    public Long getUserID() {
        return userID;
    }

    public boolean isSmsSent() {
        return isSmsSent;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

}

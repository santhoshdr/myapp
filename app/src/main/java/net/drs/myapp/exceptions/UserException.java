package net.drs.myapp.exceptions;

public class UserException  extends Exception{
 /**
     * 
     */
    private static final long serialVersionUID = 1L;

private String errorCode;
    
    private String errorMessage;
    
    
    public UserException( String errorCode , String errorMessage){
        super(errorMessage);
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }

    
    public String getErrorCode() {
        return errorCode;
    }
    
    
    public String getErrorMessage(){
        return errorMessage;
    }

}

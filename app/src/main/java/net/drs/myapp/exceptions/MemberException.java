package net.drs.myapp.exceptions;

public class MemberException  extends Exception{

    private static final long serialVersionUID = -5055224542466024012L;
    
    /**
     * 
     */
    private String errorCode;
    
    private String errorMessage;
    
    
    MemberException( String errorCode , String errorMessage){
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

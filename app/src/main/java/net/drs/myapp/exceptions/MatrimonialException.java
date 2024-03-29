package net.drs.myapp.exceptions;

public class MatrimonialException extends Exception{
    
     /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private String errorCode;
    
    private String errorMessage;
    
    
    MatrimonialException( String errorCode , String errorMessage){
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

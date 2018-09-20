package net.drs.myapp.app.exception;

public class RoleException  extends Exception{
	
	
	private String messag;
	private String faultcode;
	
	
	
	public String getMessag() {
		return messag;
	}
	public void setMessag(String messag) {
		this.messag = messag;
	}
	public RoleException(String messag, String faultcode) {
		super();
		this.messag = messag;
		this.faultcode = faultcode;
	}
	
	
	public RoleException(String messag) {
		super();
		this.messag = messag;
	}
	
	public String getFaultcode() {
		return faultcode;
	}
	public void setFaultcode(String faultcode) {
		this.faultcode = faultcode;
	}
	
	
	

}

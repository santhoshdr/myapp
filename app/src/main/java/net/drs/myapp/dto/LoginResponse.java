package net.drs.myapp.dto;

public class LoginResponse {

	public LoginResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private String accessToken;
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	private String tokenType;
	

}

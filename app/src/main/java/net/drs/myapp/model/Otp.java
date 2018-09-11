package net.drs.myapp.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "otp")
public class Otp  implements Serializable{

	@Id
    @Column(name="otpId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long otpId;
	
	private char[] uniqueOTPSent;
	
	private int otpValidFor;
	
	private Long userId;
	
	private boolean isvalidated = false;
	
	private char[] userEnteredOTP;

	private Timestamp otpSentTimeStamp;
	
	private Timestamp userOtpEnteredTimeStamp;

	public int getOtpValidFor() {
		return otpValidFor;
	}

	public void setOtpValidFor(int otpValidFor) {
		this.otpValidFor = otpValidFor;
	}

	public boolean isIsvalidated() {
		return isvalidated;
	}

	public void setIsvalidated(boolean isvalidated) {
		this.isvalidated = isvalidated;
	}

	public char[] getUniqueOTPSent() {
		return uniqueOTPSent;
	}

	public void setUniqueOTPSent(char[] uniqueOTPSent) {
		this.uniqueOTPSent = uniqueOTPSent;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOtpId() {
		return otpId;
	}

	public void setOtpId(Long otpId) {
		this.otpId = otpId;
	}

	public char[] getUserEnteredOTP() {
		return userEnteredOTP;
	}

	public void setUserEnteredOTP(char[] userEnteredOTP) {
		this.userEnteredOTP = userEnteredOTP;
	}

	public Timestamp getOtpSentTimeStamp() {
		return otpSentTimeStamp;
	}

	public void setOtpSentTimeStamp(Timestamp otpSentTimeStamp) {
		this.otpSentTimeStamp = otpSentTimeStamp;
	}

	public Timestamp getUserOtpEnteredTimeStamp() {
		return userOtpEnteredTimeStamp;
	}

	public void setUserOtpEnteredTimeStamp(Timestamp userOtpEnteredTimeStamp) {
		this.userOtpEnteredTimeStamp = userOtpEnteredTimeStamp;
	}

	
	@Override
	public String toString() {
		return "Otp [otpId=" + otpId + ", uniqueOTPSent="
				+ Arrays.toString(uniqueOTPSent) + ", otpValidFor="
				+ otpValidFor + ", userId=" + userId + ", isvalidated="
				+ isvalidated + ", userEnteredOTP="
				+ Arrays.toString(userEnteredOTP) + ", otpSentTimeStamp="
				+ otpSentTimeStamp + ", userOtpEnteredTimeStamp="
				+ userOtpEnteredTimeStamp + "]";
	}

}

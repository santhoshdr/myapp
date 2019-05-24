package net.drs.myapp.dto;

import java.io.Serializable;



/**
 * This class collects data from complete Registration Screen and stored to DB
 * @author srajanna
 *
 */
public class CompleteRegistrationDTO  extends CommonDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2529876727070747571L;
	
	
	private String fullName;
	
	private int age;
	
	private String occupation;
	
	private String location;
	
	private String gender;
	
	private String userIdorEmailAddress;
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "CompleteRegistrationDTO [fullName=" + fullName + ", age=" + age
				+ ", occupation=" + occupation + ", location=" + location
				+ ", gender=" + gender + "]";
	}

	public String getUserIdorEmailAddress() {
		return userIdorEmailAddress;
	}

	public void setUserIdorEmailAddress(String userIdorEmailAddress) {
		this.userIdorEmailAddress = userIdorEmailAddress;
	}
	

}

package net.drs.myapp.dto;

import java.io.File;
import java.io.Serializable;
import java.sql.Date;

public class UserDTO implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6811026592389788897L;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String emailAddress;

    private String address;

    private boolean isActive = false;

    private Date dateOfCreation;

    private Date lastUpdated;

    private Long id;

    private Long userId;

    private File image;

    private String password;

    private String confirmPassword;

    // used for validating string sent in email
    private String temporaryActivationString;

    // this is used at the time of resetting of password
    private String temperoryPassword;

    public String getTemperoryPassword() {
        return temperoryPassword;
    }

    public void setTemperoryPassword(String temperoryPassword) {
        this.temperoryPassword = temperoryPassword;
    }

    private Date creationDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getTemporaryActivationString() {
        return temporaryActivationString;
    }

    public void setTemporaryActivationString(String temporaryActivationString) {
        this.temporaryActivationString = temporaryActivationString;
    }

    private String createdBy;

    private Date updatedDate;

    private String updatedBy;

}

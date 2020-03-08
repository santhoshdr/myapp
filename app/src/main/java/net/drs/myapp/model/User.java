package net.drs.myapp.model;

import java.io.Serializable;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "userdetail")
public class User implements Serializable {
    /**
     * 
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName")
    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String emailAddress;

    private String address;

    private boolean isActive = false;

    private Date dateOfCreation;

    private Date lastUpdated;

    private String password;

    @Transient
    private String confirmPassword;

    private Long userId;

    @Transient
    private Set<Role> roles;

    // Date on which the account creation.
    private Date creationDate;

    // Example : after 30 days from the day of activation, the user will be
    // active
    private Date accountValidTill;

    // When the temporary Activation date was sent..
    private long temporaryActivationSentDate;

    // Example : Temporary Activation is valid of 5 minutes
    private int temporaryActivationvalidforInMinutes;

    private String temporaryActivationString;
    
    private String paymentDetails;

    private String createdBy;

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getGotram() {
        return gotram;
    }

    public void setGotram(String gotram) {
        this.gotram = gotram;
    }

    public String getClassofMembershipDesired() {
        return classofMembershipDesired;
    }

    public void setClassofMembershipDesired(String classofMembershipDesired) {
        this.classofMembershipDesired = classofMembershipDesired;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getModeofPayment() {
        return modeofPayment;
    }

    public void setModeofPayment(String modeofPayment) {
        this.modeofPayment = modeofPayment;
    }

    private Date updatedDate;

    private String relation;

    private String gotram;

    private String classofMembershipDesired;

    private Double amount;

    private String modeofPayment;

    public User() {

    }

    public User(String emailidOrName, String mobileNumber) {
        this.emailAddress = emailidOrName;
        this.mobileNumber = mobileNumber;
    }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

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

    public Date getAccountValidTill() {
        return accountValidTill;
    }

    public void setAccountValidTill(Date accountValidTill) {
        this.accountValidTill = accountValidTill;
    }

    public String getTemporaryActivationString() {
        return temporaryActivationString;
    }

    public void setTemporaryActivationString(String temporaryActivationString) {
        this.temporaryActivationString = temporaryActivationString;
    }

    public int getTemporaryActivationvalidforInMinutes() {
        return temporaryActivationvalidforInMinutes;
    }

    public void setTemporaryActivationvalidforInMinutes(int temporaryActivationvalidforInMinutes) {
        this.temporaryActivationvalidforInMinutes = temporaryActivationvalidforInMinutes;
    }

    public long getTemporaryActivationSentDate() {
        return temporaryActivationSentDate;
    }

    public void setTemporaryActivationSentDate(long temporaryActivationSentDate) {
        this.temporaryActivationSentDate = temporaryActivationSentDate;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    private String updatedBy;

}

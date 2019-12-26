package net.drs.myapp.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "email_notification")
public class Email1 {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long userID;

    private String emailId;

    // key-value pair from application.properties
    private String emailTemplateId;

    private String emailMessageSent;

    // true = Yes. Need to send email
    // false = No. Already sent.
    private boolean needtoSendEmail;

    private String emailresponse;

    private Date updatedDate;

    private String updatedBy;

    private Date creationDate;

    private String createdBy;

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate
     *            the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the updatedBy
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy
     *            the updatedBy to set
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate
     *            the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the userID
     */
    public Long getUserID() {
        return userID;
    }

    /**
     * @param userID
     *            the userID to set
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId
     *            the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the emailTemplateId
     */
    public String getEmailTemplateId() {
        return emailTemplateId;
    }

    /**
     * @param emailTemplateId
     *            the emailTemplateId to set
     */
    public void setEmailTemplateId(String emailTemplateId) {
        this.emailTemplateId = emailTemplateId;
    }

    /**
     * @return the needtoSendEmail
     */
    public boolean isNeedtoSendEmail() {
        return needtoSendEmail;
    }

    /**
     * @param needtoSendEmail
     *            the needtoSendEmail to set
     */
    public void setNeedtoSendEmail(boolean needtoSendEmail) {
        this.needtoSendEmail = needtoSendEmail;
    }

    /**
     * @return the emailresponse
     */
    public String getEmailresponse() {
        return emailresponse;
    }

    /**
     * @param emailresponse
     *            the emailresponse to set
     */
    public void setEmailresponse(String emailresponse) {
        this.emailresponse = emailresponse;
    }

    public String getEmailMessageSent() {
        return emailMessageSent;
    }

    public void setEmailMessageSent(String emailMessageSent) {
        this.emailMessageSent = emailMessageSent;
    }

    public Long getId() {
        return Id;
    }

}

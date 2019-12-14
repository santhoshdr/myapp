package net.drs.myapp.mqservice;

import java.io.Serializable;

public class NotificationRequest implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2358050464685162683L;

    private Long notificationId;

    private String emailid;

    private String template;

    private String messageContent;

    public NotificationRequest(Long notificationId, String emailid, String template, String messageContent) {
        super();
        this.notificationId = notificationId;
        this.emailid = emailid;
        this.template = template;
        this.messageContent = messageContent;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

}

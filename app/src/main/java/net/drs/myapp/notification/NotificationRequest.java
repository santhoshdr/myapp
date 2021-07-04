package net.drs.myapp.notification;

import java.io.Serializable;
import java.util.Map;

public class NotificationRequest implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2358050464685162683L;

    private Long notificationId;

    private String emailid;

    private String phoneNumber;

    private String template;

    private String emailContent;

    private Map<String, String> data;

    private NotificationType notificationType = NotificationType.EMAIL; // by
                                                                        // default

    private NotificationTemplate notificationTemplate = NotificationTemplate.NEW_REGISTRATION;

    public NotificationRequest(Long notificationId, String emailid, String phoneNumber, Map<String, String> data, NotificationTemplate notificationTemplate, NotificationType notificationType) {
        super();
        this.notificationId = notificationId;
        this.emailid = emailid;
        this.data = data;
        this.notificationType = notificationType;
        this.phoneNumber = phoneNumber;
        this.notificationTemplate = notificationTemplate;
    }

    public NotificationRequest() {
        super();
    }

    public Map<String, String> getData() {
        return data;
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

    public String getNotificationType() {
        return notificationType.getNotificationType();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public NotificationTemplate getNotificationTemplate() {
        return notificationTemplate;
    }
}

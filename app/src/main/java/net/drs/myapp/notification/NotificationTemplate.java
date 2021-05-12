package net.drs.myapp.notification;

public enum NotificationTemplate {
    
    NEW_REGISTRATION("NEW_REGISTRATION", "New Registration"), 
    FORGOT_PASSWORD("FORGOT_PASSWORD", "Forgot Password"),
    CHANGE_PASSWORD("CHANGE_PASSWORD","Change Password");

    private String notificationType;

    private String notificationDisplayName;

    private NotificationTemplate(String notificationType, String notificationDisyplayName) {
        this.notificationType = notificationType;
        this.notificationDisplayName = notificationDisyplayName;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public String getNotificationDisplayName() {
        return notificationDisplayName;
    }

}

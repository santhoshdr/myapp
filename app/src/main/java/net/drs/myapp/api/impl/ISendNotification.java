package net.drs.myapp.api.impl;

import net.drs.myapp.notification.NotificationRequest;

public interface ISendNotification {

    public void sendSMSNotification(NotificationRequest notificationRequest) throws Exception;

    public void sendEmailNotification(NotificationRequest notificationRequest) throws Exception;

}

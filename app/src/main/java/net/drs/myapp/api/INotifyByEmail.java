package net.drs.myapp.api;

import net.drs.myapp.dto.EmailDTO;
import net.drs.myapp.dto.SMSDTO;
import net.drs.myapp.notification.NotificationRequest;

public interface INotifyByEmail {

    Long insertDatatoDBforNotification(EmailDTO emailDTO);

    boolean updateNotificationafterSending();

    // read values from DB and send notification..
    boolean sendNotofication();

    // for SMS
    SMSDTO insertDatatoDBforNotification(SMSDTO smsDTO);

    // using direct call - NOT RABBITMQ
    boolean sendNotoficationDirectly(NotificationRequest emailDTO) throws Exception;

    // using direct call - NOT RABBITMQ
}

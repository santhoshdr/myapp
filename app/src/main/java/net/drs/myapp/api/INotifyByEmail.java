package net.drs.myapp.api;

import net.drs.myapp.dto.EmailDTO;

public interface INotifyByEmail {

    Long insertDatatoDBforNotification(EmailDTO emailDTO);

    boolean updateNotificationafterSending();

    // read values from DB and send notification..
    boolean sendNotofication();

}

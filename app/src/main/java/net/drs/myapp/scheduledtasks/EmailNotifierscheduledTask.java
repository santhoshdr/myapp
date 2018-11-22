package net.drs.myapp.scheduledtasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import net.drs.myapp.api.INotifyByEmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailNotifierscheduledTask {


    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    INotifyByEmail notificationByEmailService;
    
    @Scheduled(fixedRate = 24000)
    public void scheduleTaskWithFixedRate() {
        notificationByEmailService.sendNotofication();	
    }
    
    
    @Scheduled(fixedRate = 1000)
    public void scheduleTaskWithFixedRate1() {
    	notificationByEmailService.sendNotofication();
    	System.out.println("Fixed Rate Task :: Execution Time - {}"+ dateTimeFormatter.format(LocalDateTime.now()));
    }

}
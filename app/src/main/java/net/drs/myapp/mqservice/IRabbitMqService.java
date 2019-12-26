package net.drs.myapp.mqservice;

import net.drs.common.notifier.NotificationRequest;

public interface IRabbitMqService {
	
	
	void publishSMSMessage(NotificationRequest nRequest);
	
	

}

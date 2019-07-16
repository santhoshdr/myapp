package net.drs.myapp.mqservice;

public interface IRabbitMqService {
	
	
	void publishSMSMessage(NotificationRequest nRequest);
	
	

}

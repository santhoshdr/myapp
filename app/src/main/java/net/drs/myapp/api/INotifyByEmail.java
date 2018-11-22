package net.drs.myapp.api;

import net.drs.myapp.dto.EmailDTO;

public interface INotifyByEmail {
	
	
	boolean insertDatatoDBforNotification(EmailDTO emailDTO);
	
	
	// read values from DB and send notification.. 
	boolean sendNotofication();	
	
	
	

}

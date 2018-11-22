package net.drs.myapp.dao;

import java.util.List;

import net.drs.myapp.model.Email;

public interface INotifyByEmailDAO {

	
	public boolean insertEmailDatailstoDB(Email email);
	
	
	public List getEntriesTowhichEmailNeedstobeSent();
	
}

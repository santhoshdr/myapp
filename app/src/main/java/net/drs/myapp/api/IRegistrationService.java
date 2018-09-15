package net.drs.myapp.api;

import net.drs.myapp.model.Fotographer;
import net.drs.myapp.model.User;

public interface IRegistrationService {
	
	boolean adduser(User user) throws Exception;
	
	boolean addFotographer(Fotographer fotographer);
}

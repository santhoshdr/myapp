package net.drs.myapp.resource;

import net.drs.myapp.config.UserPrincipal;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.model.User;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

public abstract class GenericService {

    
    private HttpSession session;
    
    protected HttpSession getUserSession() {
        return this.session ;
    }
    
    
    protected void setValueInUserSession(HttpSession session ,Object value) {
         session.setAttribute(ApplicationConstants.LOGGED_IN_USER_NAME, value);
    }
    
    protected Long getLoggedInUserId() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getId();
    }

    protected String getLoggedInUserPassword() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getPassword();
    }

    protected UserPrincipal getLoggedInUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal;
    }
    
    protected String getLoggedInUserName() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getUsername();
    }
}

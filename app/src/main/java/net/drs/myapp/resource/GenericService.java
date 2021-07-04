package net.drs.myapp.resource;

import net.drs.myapp.config.UserPrincipal;
import net.drs.myapp.constants.ApplicationConstants;
import net.drs.myapp.model.User;

import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;

import com.paytm.pg.merchant.CheckSumServiceHelper;

public abstract class GenericService {

    
    private HttpSession session;
    
    protected HttpSession getUserSession() {
        return this.session ;
    }
    

    protected HttpSession setUserSession(HttpSession session) {
        return this.session ;
    }
    
    
    protected void setValueInUserSession(HttpSession session ,String key, Object value) {
         session.setAttribute(key, value);
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
    
    protected String getLoggedInUserRole() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "";
    }
    
    protected boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
    	parameters.remove("token");
        return CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum("ymYFiyrKDkr4QAHF",
                parameters, paytmChecksum);
    }
    
    protected String getJWTfromsession() {
    	return (String) session.getAttribute("token");
    }

    protected void setJWTinsession(HttpSession session, String token) {
    	session.setAttribute("token",token);
    	this.session = session;
    }

    
}



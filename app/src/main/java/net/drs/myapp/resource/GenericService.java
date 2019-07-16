package net.drs.myapp.resource;

import net.drs.myapp.config.UserPrincipal;

import org.springframework.security.core.context.SecurityContextHolder;

public abstract class GenericService {
    
    
    protected Long getLoggedInUser(){
        UserPrincipal userPrincipal= (UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getId();
    }

}

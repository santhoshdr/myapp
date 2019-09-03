package net.drs.myapp.resource;

import net.drs.myapp.config.UserPrincipal;
import net.drs.myapp.model.User;

import org.springframework.security.core.context.SecurityContextHolder;

public abstract class GenericService {

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
}

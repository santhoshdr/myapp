package net.drs.myapp.utils;

public enum Role {

    USER("ROLE_USER","USER"), ADMIN("ROLE_ADMIN","ADMIN"), MATRIMONY("ROLE_MATRIMONY","MATRIMONY");

    private String role;
    
    private String roleDisplayname;

    Role(String role,String displayName) {
        this.role = role;
        this.roleDisplayname = displayName;
    }

    public String getRole() {
        return role;
    }
    
    public String getRoleDisplayName() {
    	return roleDisplayname;
    }

}

package net.drs.myapp.utils;

public enum Role {

    USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), MATRIMONY("ROLE_MATRIMONY");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}

package net.drs.myapp.utils;

public enum MaritalStatus {

    SINGLE("SINGLE"), DIVORCED("DICORCED");

    private String status;

    MaritalStatus(String status) {
        this.status = status;
    }

    public String getMaritalStatus() {
        return status;
    }
}

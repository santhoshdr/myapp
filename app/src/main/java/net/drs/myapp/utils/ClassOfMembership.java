package net.drs.myapp.utils;

public enum ClassOfMembership {

    DONER("DONER"), PATRON("PATRON"), LIFETIMEMEMBER("LIFE TIME MEMBER");

    private String classOfMembership;

    ClassOfMembership(String classOfMembership) {
        this.classOfMembership = classOfMembership;
    }

    public String getClassOfMembership() {
        return classOfMembership;
    }

}

package net.drs.myapp.utils;

public enum Gotras {

    KAUSHIKASA("KAUSHIKASA"), 
    AGASTYA("AGASTYA"),
    AUDALA("AUDALA"),
    ANGIRASA("ANGIRASA"),
    ATRI("ATRI"),
    AATREYA("AATREYA"),
    BHARADWAJ("BHARADWAJ"),
    BHARGAVA("BHARGAVA"),
    BHRIGU("BHRIGU"),
    BRIHADBALA("BRIHADBALA"),
    CHANDRATRE("CHANDRATRE"),
    DHANANJAYA("DHANANJAYA"),
    GARG("GARG"),
    GAUTAM("GAUTAM"),
    HARINAMA("HARINAMA"),
    HARITASYA("HARITASYA"),
    JAMADAGNI("JAMADAGNI"),
    KADAM("KADAM"),
    KASHYAPA("KASHYAPA"),
    KAUNDINYA("KAUNDINYA"),
    MANU("MANU"),
    MARICHI("MARICHI"),
    MEENA("MEENA"),
    PARASHAR("PARASHAR"),
    SANDILYA("SANDILYA"),
    SHIVA("SHIVA"),
    SIWAL("SIWAL"),
    UPAMANYU("UPAMANYU"),
    UPRETI("UPRETI"),
    VASHISTA("VASHISTA"),
    VISHNU("VISHNU"),
    VISHVAMITRA("VISHVAMITRA"),
    YADAV("YADAV");

    private String gotraName;
    
    Gotras(String gotraName) {
        this.gotraName = gotraName;
    }

    public String getGotraName() {
        return gotraName;
    }

}

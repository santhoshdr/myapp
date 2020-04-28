package net.drs.myapp.dto;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;

import net.drs.myapp.utils.MaritalStatus;

/**
 * WED means to marry someone.
 * 
 * @author srajanna
 *
 */

public class WedDTO extends CommonDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 3748352921650894654L;

    public WedDTO() {
        super();
    }

    private MaritalStatus maritalStatus = MaritalStatus.SINGLE;

 private String fatherFullName;
    
    private String motherFullName;
    
    public String getFatherFullName() {
        return fatherFullName;
    }

    public void setFatherFullName(String fatherFullName) {
        this.fatherFullName = fatherFullName;
    }

    public String getMotherFullName() {
        return motherFullName;
    }

    public void setMotherFullName(String motherFullName) {
        this.motherFullName = motherFullName;
    }

    public String getWedGotram() {
        return wedGotram;
    }

    public void setWedGotram(String wedGotram) {
        this.wedGotram = wedGotram;
    }

    // father can add their siblings. Father -> would have registered, but
    // updating son/daughters data.
    // bydefault = true
    private Boolean isProfileActive = true;

    private Long id;

    private Date dateOfBirth;

    private String timeOfBirth;

    private String placeOfBirth;

    private Boolean makePublic;

    private String wedFullName;
    
    private int wedAge;

    private String wedGender;

    private String wedOccupation;

    private String wedRaashi;
    
    private String wedGotram;

    private String wedNakshtra;

    private String wedPlace;
    // optional
    private String wedMobilenumber;

    private File[] wedImage;

    private File[] wedJataka;

    public String getWedFullName() {
        return wedFullName;
    }

    public void setWedFullName(String wedFullName) {
        this.wedFullName = wedFullName;
    }

    public int getWedAge() {
        return wedAge;
    }

    public void setWedAge(int wedAge) {
        this.wedAge = wedAge;
    }

    public String getWedGender() {
        return wedGender;
    }

    public void setWedGender(String wedGender) {
        this.wedGender = wedGender;
    }

    public String getWedOccupation() {
        return wedOccupation;
    }

    public void setWedOccupation(String wedOccupation) {
        this.wedOccupation = wedOccupation;
    }

    public String getWedRaashi() {
        return wedRaashi;
    }

    public void setWedRaashi(String wedRaashi) {
        this.wedRaashi = wedRaashi;
    }

    public String getWedNakshtra() {
        return wedNakshtra;
    }

    public void setWedNakshtra(String wedNakshtra) {
        this.wedNakshtra = wedNakshtra;
    }

    public String getWedPlace() {
        return wedPlace;
    }

    public void setWedPlace(String wedPlace) {
        this.wedPlace = wedPlace;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Timestamp getTimeOfBirth() {
        Timestamp ts =null;
        if(timeOfBirth!=null) {
           ts = Timestamp.valueOf(timeOfBirth);    
        }
        return ts;
    }

    public void setTimeOfBirth(String timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getWedMobilenumber() {
        return wedMobilenumber;
    }

    public void setWedMobilenumber(String wedMobilenumber) {
        this.wedMobilenumber = wedMobilenumber;
    }

    public File[] getWedImage() {
        return wedImage;
    }

    public Boolean getMakePublic() {
        return makePublic;
    }

    public void setMakePublic(Boolean makePublic) {
        this.makePublic = makePublic;
    }

    public void setWedImage(File[] wedImage) {
        this.wedImage = wedImage;
    }

    public File[] getWedJataka() {
        return wedJataka;
    }

    public void setWedJataka(File[] wedJataka) {
        this.wedJataka = wedJataka;
    }

    @Override
    public String toString() {
        return "Wed [wedFullName=" + wedFullName + ", wedAge=" + wedAge + ", wedGender=" + wedGender + ", wedOccupation=" + wedOccupation + ", wedRaashi=" + wedRaashi + ", wedNakshtra=" + wedNakshtra
                + ", wedPlace=" + wedPlace + ", wedMobilenumber=" + wedMobilenumber + ", wedImage=" + Arrays.toString(wedImage) + ", wedJataka=" + Arrays.toString(wedJataka) + "]";
    }

    public Boolean getIsProfileActive() {
        return isProfileActive;
    }

    public void setIsProfileActive(Boolean isProfileActive) {
        this.isProfileActive = isProfileActive;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

}

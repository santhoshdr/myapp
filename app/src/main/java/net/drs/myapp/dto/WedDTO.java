package net.drs.myapp.dto;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

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

    private String folderId;
    
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

    private String dateOfBirth;

    private String timeOfBirth;

    public String getTimeOfBirth() {
        return timeOfBirth;
    }

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

     private  MultipartFile[] wedImage;
     
     private MultipartFile[] wedJataka;
     
     private  String[]  wedImageFilePath;
     
     private String[] wedJatakaFilePath;

     private String[] wedJatakaFileName;
     
    public MultipartFile[] getWedImage() {
        return wedImage;
    }

    public void setWedImage(MultipartFile[] wedImage) {
        this.wedImage = wedImage;
    }

    public MultipartFile[] getWedJataka() {
        return wedJataka;
    }

    public void setWedJataka(MultipartFile[] wedJataka) {
        this.wedJataka = wedJataka;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimeOfBirth(String timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
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

    public Boolean getMakePublic() {
        return makePublic;
    }

    public void setMakePublic(Boolean makePublic) {
        this.makePublic = makePublic;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String[] getWedImageFilePath() {
        return wedImageFilePath;
    }

    public void setWedImageFilePath(String[] wedImageFilePath) {
        this.wedImageFilePath = wedImageFilePath;
    }

    public String[] getWedJatakaFilePath() {
        return wedJatakaFilePath;
    }

    public void setWedJatakaFilePath(String[] wedJatakaFilePath) {
        this.wedJatakaFilePath = wedJatakaFilePath;
    }

    public String[] getWedJatakaFileName() {
        return wedJatakaFileName;
    }

    public void setWedJatakaFileName(String[] wedJatakaFileName) {
        this.wedJatakaFileName = wedJatakaFileName;
    }


    
}

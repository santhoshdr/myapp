package net.drs.myapp.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import net.drs.myapp.dto.CommonDTO;
import net.drs.myapp.utils.MaritalStatus;

/**
 * WED means to marry someone.
 * 
 * @author srajanna
 *
 */

@Entity
@Table(name = "wed")
public class Wed extends CommonDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 5811416485369286053L;

    public Wed() {
        super();
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // father can add their siblings. Father -> would have registered, but
    // updating son/daughters data.
    private Long addedBy;

    private Boolean isProfileActive;

    private MaritalStatus maritalStatus;

    private Date dateOfBirth;

    private Timestamp timeOfBirth;

   

    private String placeOfBirth;

    private String wedFullName;

    private int wedAge;

    private String wedGender;

    private String wedOccupation;

    private String wedRaashi;

    private String wedNakshtra;

    private String wedPlace;
    // optional
    private String wedMobilenumber;

    public Long getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Long addedBy) {
        this.addedBy = addedBy;
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

    public String getWedMobilenumber() {
        return wedMobilenumber;
    }

    public void setWedMobilenumber(String wedMobilenumber) {
        this.wedMobilenumber = wedMobilenumber;
    }

    public Long getId() {
        return id;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
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

    @Override
    public String toString() {
        return "Wed [id=" + getId() + ", addedBy=" + addedBy + ", wedFullName=" + wedFullName + ", wedAge=" + wedAge + ", wedGender=" + wedGender + ", wedOccupation=" + wedOccupation + ", wedRaashi="
                + wedRaashi + ", wedNakshtra=" + wedNakshtra + ", wedPlace=" + wedPlace + ", wedMobilenumber=" + wedMobilenumber + "]";
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

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimeOfBirth() {
        return timeOfBirth;
    }

    public void setTimeOfBirth(Timestamp timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

}

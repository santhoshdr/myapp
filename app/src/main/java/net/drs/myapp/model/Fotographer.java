package net.drs.myapp.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "Fotographer_detail")
public class Fotographer {
	
	@Id
    @Column(name="fotographerId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long fotographerId;
 	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String mobilenumber;
	
	private String image;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="fotographerId")
	private Set<Address> address;
	
	@Transient
	
	@OneToMany(mappedBy="comments")
	private Set<Comments> comments;
	
	// add ratings later
	public Fotographer() {

	}

	private boolean isactive = false;
	
	private Date registeredDate;
	
	//private List<ProvidedServices> providedServices;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}


	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}
	
	public Long getFotographerId() {
		return fotographerId;
	}

	public void setFotographerId(Long fotographerId) {
		this.fotographerId = fotographerId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<Comments> getComments() {
		return comments;
	}

	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}


}

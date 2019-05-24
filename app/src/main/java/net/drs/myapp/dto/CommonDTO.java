package net.drs.myapp.dto;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;


@Inheritance(
	    strategy = InheritanceType.JOINED
	)
public class CommonDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String createdBy;
	
	private Date updatedDate;
	
	private String updatedBy;
	
	private Date createdDate;
	
	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	
	
	
}

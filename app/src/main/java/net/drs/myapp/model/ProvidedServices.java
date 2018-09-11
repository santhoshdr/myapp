package net.drs.myapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Fotographer_Services")
public class ProvidedServices {
	
	@Id
    @Column(name="servicesID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long servicesID;
	
	private Long photographerID;
	
	private String instantPhoto;
	
	private String homeDelivery;
	
	private String serviceName;
	
	private String serviceCost;

	
	
	public Long getServicesID() {
		return servicesID;
	}

	public void setServicesID(Long servicesID) {
		this.servicesID = servicesID;
	}

	public Long getPhotographerID() {
		return photographerID;
	}

	public void setPhotographerID(Long photographerID) {
		this.photographerID = photographerID;
	}

	public String getInstantPhoto() {
		return instantPhoto;
	}

	public void setInstantPhoto(String instantPhoto) {
		this.instantPhoto = instantPhoto;
	}

	public String getHomeDelivery() {
		return homeDelivery;
	}

	public void setHomeDelivery(String homeDelivery) {
		this.homeDelivery = homeDelivery;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceCost() {
		return serviceCost;
	}

	public void setServiceCost(String serviceCost) {
		this.serviceCost = serviceCost;
	}
	
	
	

}

package org.thumb.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({ 
	@NamedQuery(name = "getAllPlaces", 
        query = "SELECT p FROM Place p"),    
    @NamedQuery(name = "deleteAllPlaces", 
        query = "DELETE FROM Place"),         
})
@Entity @IdClass(PlaceId.class)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Place {
	
	@Id
	@XmlElement
	private String id;
	
	@Id
	@XmlElement
	private String provider;	
	
	@NotNull
	@XmlElement
	private String name;	

	@XmlElement
	private Double latitude;

	@XmlElement
	private Double longitude;

	public Place() {
		super();		
	}
	
	public Place(String id, String name, String provider, Double latitude, Double longitude) {
		super();
		this.id = id;
		this.name = name;
		this.provider = provider;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}		
	
}

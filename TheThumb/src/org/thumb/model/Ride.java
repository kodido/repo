package org.thumb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({ 
	@NamedQuery(name = "getAllRides", 
        query = "SELECT r FROM Ride r"),
    @NamedQuery(name = "getRideById", 
        query = "SELECT r FROM Ride r WHERE r.id = :id"),        
    @NamedQuery(name = "deleteAllRides", 
        query = "DELETE FROM Ride"),         
})
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Ride {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	private Long id;
	
	@ManyToOne
	@NotNull
	@XmlElement	
	private Place origin;

	@ManyToOne
	@NotNull
	@XmlElement
	private Place destination;
			
	@Temporal(TemporalType.DATE)
	@XmlElement
	@NotNull
	private Date date;	

	@ManyToOne
	@NotNull
	@XmlElement
	private UserProfile driver; 
	
	/* TODO: eager fetching might be a performance problem */
	@ManyToMany(fetch=FetchType.EAGER)
	@NotNull
	List<UserProfile> passengers = new ArrayList<UserProfile>();

	private Integer capacity;
	
	public Ride(Place origin, Place destination, Date date, 
			UserProfile driver, Integer capacity) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.date = date;
		this.driver = driver;
		this.capacity = capacity;
	}

	public Ride() {
		super();
	}	
	
	public UserProfile getDriver() {
		return driver;
	}

	public void setDriver(UserProfile driver) {
		this.driver = driver;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Place getOrigin() {
		return origin;
	}

	public void setOrigin(Place origin) {
		this.origin = origin;
	}

	public Place getDestination() {
		return destination;
	}

	public void setDestination(Place destination) {
		this.destination = destination;
	}

	public List<UserProfile> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<UserProfile> passengers) {
		this.passengers = passengers;
	}

	public Long getId() {
		return id;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
}

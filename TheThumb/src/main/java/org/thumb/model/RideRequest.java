package org.thumb.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@NamedQueries({ 
	@NamedQuery(name = "getRideRequestsByRideDriver", 
        query = "select rq from RideRequest rq JOIN rq.ride r where r.driver.userName = :driver"),
   	@NamedQuery(name = "deleteAllRideRequests", 
        query = "delete from RideRequest"),         
        
})
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RideRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	private Long id;
	
	@ManyToOne		
	@XmlElement
	@NotNull
	private Ride ride;

	@ManyToOne
	@XmlElement
	@NotNull
	private UserProfile requester;		
		
	@XmlElement
	@Enumerated(EnumType.STRING)
	@NotNull
	private ApprovalState status;			
	
	public RideRequest(Ride ride, UserProfile requester) {
		super();
		this.ride = ride;
		this.requester = requester;
		this.status = ApprovalState.INITIAL;
	}		

	public RideRequest() {
		super();
	}	
	

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public UserProfile getRequester() {
		return requester;
	}

	public void setRequester(UserProfile requester) {
		this.requester = requester;
	}

	public ApprovalState getStatus() {
		return status;
	}

	public void setStatus(ApprovalState status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}	
	
}

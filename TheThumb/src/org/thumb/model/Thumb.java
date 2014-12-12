package org.thumb.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@NamedQuery(name = "getAllThumbs", 
        query = "SELECT t FROM Thumb t"),   
        @NamedQuery(name = "deleteAllThumbs", 
        query = "DELETE FROM Thumb"),               
})
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Thumb {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	private long id;
	
    @ManyToOne	
	@NotNull
	@XmlElement
	private Place origin;

	@ManyToOne
	@NotNull
	@XmlElement
	private Place destination;
		
	@ManyToOne
	@NotNull
	@XmlElement	
	private UserProfile hitchiker;	 		
	
	@Temporal(TemporalType.DATE)
	@XmlElement
	private Date date;
	
	public Thumb(Place origin, Place destination, Date date, UserProfile hitchiker) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.date = date;
		this.hitchiker = hitchiker;
	}

	public Thumb() {
		super();
	}	
	
	public UserProfile getHitchiker() {
		return hitchiker;
	}

	public void setHitchiker(UserProfile hitchiker) {
		this.hitchiker = hitchiker;
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
	
}

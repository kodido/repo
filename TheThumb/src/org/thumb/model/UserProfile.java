package org.thumb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@NamedQueries({  
	@NamedQuery(name = "getAllUserProfiles", 
	        query = "SELECT p FROM UserProfile p"),	
    @NamedQuery(name = "getUserProfileByName", 
		        query = "SELECT p FROM UserProfile p WHERE p.userName = :name"),
    @NamedQuery(name = "deleteAllUserProfiles", 
		        query = "DELETE FROM UserProfile"),       
})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 	
	@XmlElement
	private long id;
	
	@NotNull
	@XmlElement	
	private String userName;
	
	public UserProfile() {
		super();
	}	
	
	public UserProfile(String userName) {
		super();
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getId() {
		return id;
	}

}

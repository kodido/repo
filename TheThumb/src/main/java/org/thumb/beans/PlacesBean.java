package org.thumb.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.thumb.model.Place;
import org.thumb.model.PlaceId;

@Stateless
@Path("/place")
public class PlacesBean {

	@PersistenceContext
	EntityManager em;	
	
    @GET 
    @Produces("application/json")
    public List<Place> getAllPlaces() {
    	return em.createNamedQuery("getAllPlaces", Place.class).getResultList();
    }

    @Deprecated
	public void deleteAllPlaces() {
		em.createNamedQuery("deleteAllPlaces").executeUpdate();						
	}	
    
    public Place getManagedPlace(Place unmanagedPlace) {
		Place existing = em.find(Place.class, new PlaceId(unmanagedPlace.getId(), unmanagedPlace.getProvider()));
		if (existing == null) {
			em.persist(unmanagedPlace);
			return unmanagedPlace;
		} else {
			return existing;
		}    	
    }
	
}

package org.thumb.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.thumb.model.Thumb;

/**
 * Session Bean implementation class RidesBean
 */
@Stateless
@Path("/thumb")
public class ThumbsBean {

	@PersistenceContext
	EntityManager em;
	
	@EJB
	private PlacesBean placesBean;
	
    /**
     * Default constructor. 
     */
    public ThumbsBean() {
    }
    
    @GET 
    @Produces("application/json")
    public List<Thumb> getAllRides() {
    	return em.createNamedQuery("getAllThumbs", Thumb.class).getResultList();
    }
    
    @POST
    @Consumes("application/json")
    public Thumb addThumb(Thumb newThumb) {
    	newThumb.setOrigin(placesBean.getManagedPlace(newThumb.getOrigin()));
    	newThumb.setDestination(placesBean.getManagedPlace(newThumb.getDestination()));
    	
        em.persist(newThumb);
        return newThumb;
    }

    @Deprecated
	public void deleteAllThumbs() {
		em.createNamedQuery("deleteAllThumbs").executeUpdate();						
	}    

}

package org.thumb.beans;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.thumb.model.RideRequest;

/**
 * Session Bean implementation class RidesBean
 */
@Stateless
@Path("/request")
public class RideRequestsBean {

	@PersistenceContext
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public RideRequestsBean() {
    }
        
    @POST
    @Consumes("application/json")
    public RideRequest saveRideRequest(RideRequest rideRequest) {
    	if (rideRequest.getId() == null) {
            em.persist(rideRequest);
    	} else {
    		em.merge(rideRequest);
    	}
    	return rideRequest;        
    }    

    @Deprecated
	public void deleteAllRideRequests() {
		em.createNamedQuery("deleteAllRideRequests").executeUpdate();		
	}

}

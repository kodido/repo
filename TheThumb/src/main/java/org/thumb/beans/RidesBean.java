package org.thumb.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.thumb.model.Ride;
import org.thumb.model.UserProfile;

/**
 * Session Bean implementation class RidesBean
 */
@Stateless
@Path("/ride")
public class RidesBean {

	@PersistenceContext
	EntityManager em;
	
	@EJB
	PlacesBean placesBean;

	@EJB
	UserProfilesBean userProfilesBean;	
	
    /**
     * Default constructor. 
     */
    public RidesBean() {
    }
    
    @GET 
    @Produces("application/json")
    public List<Ride> getAllRides() {
    	List<Ride> result = em.createNamedQuery("getAllRides", Ride.class).getResultList();
    	return result;
    }
    
    @POST
    @Consumes("application/json")
    public Ride addRide(Ride newRide) {
    	newRide.setOrigin(placesBean.getManagedPlace(newRide.getOrigin()));
    	newRide.setDestination(placesBean.getManagedPlace(newRide.getDestination()));    	
    	newRide.setDriver(userProfilesBean.getManagedUser(newRide.getDriver()));    	
    	if (newRide.getId() == null)
            em.persist(newRide);
    	else 
    		em.merge(newRide);
        return newRide;
    }   

	@Deprecated
	public void deleteAllRides() {
		em.createNamedQuery("deleteAllRides").executeUpdate();		
	}
	
    @GET
    @Path("/{rideId}/passengers")
    @Produces("application/json")
    public List<UserProfile> getPassengersForRide(@PathParam("rideId") long rideId) {
        TypedQuery<Ride> query = em.createNamedQuery("getRideById", Ride.class);
        query.setParameter("id", rideId);
        Ride ride = query.getSingleResult();        
        return ride.getPassengers();
    }	
    
    @POST
    @Path("/{rideId}/passengers")
    @Consumes("application/json")
    public Ride addPassengerToRide(UserProfile passenger, @PathParam("rideId") long rideId) {
        TypedQuery<Ride> query = em.createNamedQuery("getRideById", Ride.class);
        query.setParameter("id", rideId);
        Ride ride = query.getSingleResult();  
        ride.getPassengers().add(passenger);
        em.merge(ride);
        return ride;
    }    
    
    @DELETE
    @Path("/{rideId}/passengers/{passengerId}")
    @Consumes("application/json")
    public Ride removePassengerFromRide(@PathParam("rideId") long rideId, @PathParam("passengerId") long passengerId) {
        TypedQuery<Ride> query = em.createNamedQuery("getRideById", Ride.class);
        query.setParameter("id", rideId);
        Ride ride = query.getSingleResult();   
        UserProfile passengerToRemove = null;
        for (UserProfile passenger : ride.getPassengers()) {
        	if (passengerId == passenger.getId()) {
        		passengerToRemove = passenger;
        		break;
        	}
		}
		ride.getPassengers().remove(passengerToRemove);
        em.merge(ride);
        return ride;
    }          

}

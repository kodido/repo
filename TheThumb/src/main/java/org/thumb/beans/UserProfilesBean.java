package org.thumb.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.thumb.model.Place;
import org.thumb.model.PlaceId;
import org.thumb.model.RideRequest;
import org.thumb.model.UserProfile;

@Stateless
@Path("/userprofile")
public class UserProfilesBean {

	@PersistenceContext
	EntityManager em;
	
    @GET
    @Produces("application/json")
    public List<UserProfile> getAllUserProfiles() {
    	return em.createNamedQuery("getAllUserProfiles", UserProfile.class).getResultList();
    }	
	
    @GET
    @Path("/{name}")
    @Produces("application/json")
    public UserProfile getUserProfileByName(@PathParam("name") String userName) {
        TypedQuery<UserProfile> query = em.createNamedQuery("getUserProfileByName", UserProfile.class);
        query.setParameter("name", userName);
        UserProfile result = query.getSingleResult(); 
        return result;
    }

    @GET
    @Path("/{name}/requests")
    @Produces("application/json")
    public List<RideRequest> getRideRequests(@PathParam("name") String userName) {
        TypedQuery<RideRequest> query = em.createNamedQuery("getRideRequestsByRideDriver", RideRequest.class);
        query.setParameter("driver", userName);
        List<RideRequest> result = query.getResultList(); 
        return result;
    }    
    
    @Deprecated
	public void deleteAllUsers() {
		em.createNamedQuery("deleteAllUserProfiles").executeUpdate();				
	}

	public UserProfile getManagedUser(UserProfile unmanagedUser) {
		UserProfile existing = em.find(UserProfile.class, unmanagedUser.getId());
		if (existing == null) {
			em.persist(unmanagedUser);
			return unmanagedUser;
		} else {
			return existing;
		}
	}	        
	
}

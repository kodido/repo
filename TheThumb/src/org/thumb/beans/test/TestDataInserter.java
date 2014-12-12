package org.thumb.beans.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.thumb.beans.PlacesBean;
import org.thumb.beans.RideRequestsBean;
import org.thumb.beans.RidesBean;
import org.thumb.beans.ThumbsBean;
import org.thumb.beans.UserProfilesBean;
import org.thumb.model.Place;
import org.thumb.model.Ride;
import org.thumb.model.RideRequest;
import org.thumb.model.Thumb;
import org.thumb.model.UserProfile;

@Singleton
@Startup
public class TestDataInserter {
	
    @PersistenceContext
    private EntityManager em;	
	
    @EJB
    private RidesBean ridesBean;
    
    @EJB
    private PlacesBean placesBean;
    
    @EJB
    private ThumbsBean thumbsBean;
    
    @EJB
    private UserProfilesBean userProfilesBean;

    @EJB
	private RideRequestsBean rideRequestsBean;        
    
	@PostConstruct
    public void insertTestData() {
		
		placesBean.deleteAllPlaces();
		ridesBean.deleteAllRides();
		userProfilesBean.deleteAllUsers();
		thumbsBean.deleteAllThumbs();
		rideRequestsBean.deleteAllRideRequests();
		
		if (ridesBean.getAllRides().size() == 0) {
	
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			Date tomorrow = cal.getTime();

			UserProfile stavri = new UserProfile("Stavri");
			em.persist(stavri);
					
			Place sofia = new Place("ChIJ9Xsxy4KGqkARYF6_aRKgAAQ", "Sofia", "GOOGLE", 42.6977082d, 23.321867500000053);
			Place varna = new Place("ChIJodfzqotTpEARfIulcRyUJ1c", "Varna", "GOOGLE", 43.2140504d, 27.914733299999966);			

			Ride ride = new Ride(sofia, varna, tomorrow, stavri, 3);
			ride.getPassengers().add(stavri);
			em.persist(sofia);
			em.persist(varna);
		
			em.persist(ride);					
			
			UserProfile dido = new UserProfile("dido");									
			em.persist(dido);
			
			
			Thumb thumb = new Thumb(sofia, varna, tomorrow, dido);						
					
			em.persist(thumb);
			
			RideRequest request = new RideRequest(ride, dido);
			em.persist(request);
						
			Place plovdiv = new Place("ChIJPXZIogjRrBQRoDgTb_rRcGQ", "Plovdiv", "GOOGLE", 42.1354079d, 24.74529039999993);
			em.persist(plovdiv);
			Ride ride2Plovdiv = new Ride(varna, plovdiv, tomorrow, dido, 1);
			List<UserProfile> passengers2Plovdiv = ride2Plovdiv.getPassengers();
			passengers2Plovdiv.add(dido);
			ride2Plovdiv.setPassengers(passengers2Plovdiv);
			em.persist(ride2Plovdiv);
			RideRequest rq = new RideRequest(ride2Plovdiv, stavri);
			em.persist(rq);			
		}		
    }
    
}

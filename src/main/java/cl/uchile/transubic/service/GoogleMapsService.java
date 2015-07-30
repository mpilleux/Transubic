package cl.uchile.transubic.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cl.uchile.transubic.calendarEvent.model.CalendarEvent;
import cl.uchile.transubic.user.model.User;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

@Service("googleMapsService")
public class GoogleMapsService {

	private GeoApiContext geoApiContext = new GeoApiContext()
			.setApiKey("AIzaSyAwL4a_JOLh8XW1E1A2rOadLZNo_x9wfEc");
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("calendarEventService")
	private CalendarEventService calendarEventService;

	public GeoApiContext getGeoApiContext() {
		return geoApiContext;
	}

	public DirectionsRoute[] getRoute(Double lat, Double lng,
			CalendarEvent calendarEvent) throws Exception {
		DirectionsRoute[] routes = DirectionsApi
				.newRequest(this.getGeoApiContext()).mode(TravelMode.TRANSIT)
				.units(Unit.METRIC).language("es").region("CL")
				.origin(new LatLng(lat, lng))
				.arrivalTime(new DateTime(calendarEvent.getEventDateTime()))
				.destination(calendarEvent.getLocation()).await();

		return routes;
	}
	
	public DirectionsRoute[] getNextCalendarEventRoute(Double lat, Double lng,
			String userKey) throws Exception {
		
		User user = this.userService.getUserByEncodedKey(userKey);
		CalendarEvent calendarEvent = this.calendarEventService
				.getNextCalendarEventsByUserIdAndDate(user,
						new Date());

		if (calendarEvent == null)
			throw new Exception();
		

		return this.getRoute(lat, lng, calendarEvent);
	}
}

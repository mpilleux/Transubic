package cl.uchile.transubic.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cl.uchile.transubic.calendarEvent.model.CalendarEvent;
import cl.uchile.transubic.service.CalendarEventService;
import cl.uchile.transubic.service.GoogleMapsService;
import cl.uchile.transubic.service.UserService;
import cl.uchile.transubic.user.model.User;

import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.LatLng;

@Controller
@RequestMapping(value = { "/direction" })
public class DirectionController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("calendarEventService")
	private CalendarEventService calendarEventService;

	@Autowired
	@Qualifier("googleMapsService")
	private GoogleMapsService googleMapsService;

	

	@RequestMapping(value = { "/getMap/{key}" }, method = RequestMethod.GET)
	public String getMap(@PathVariable("key") String key,
			@RequestParam(value = "lat") Double lat,
			@RequestParam(value = "lng") Double lng, Model model) {

		User user = this.userService.getUserByEncodedKey(key);
		CalendarEvent calendarEvent = this.calendarEventService
				.getNextCalendarEventsByUserIdAndDate(user, new Date());

		if (calendarEvent == null)
			return "redirect:/";

		model.addAttribute("region", "CL");
		model.addAttribute("language", "es");
		model.addAttribute("origin", new LatLng(lat, lng));
		model.addAttribute("destination", calendarEvent.getLocation());
		model.addAttribute("arrivalTime", calendarEvent.getEventDateTime()
				.getTime());
		model.addAttribute("travelMode",
				"google.maps.DirectionsTravelMode.TRANSIT");

		return "directions/mapa";
	}

	@RequestMapping(value = { "/requiresMap/{key}" }, method = RequestMethod.GET)
	@ResponseBody
	public String requiresMap(@PathVariable("key") String key,
			@RequestParam(value = "lat") Double lat,
			@RequestParam(value = "lng") Double lng, Model model) {

		DirectionsRoute[] routes = null;

		try {
			routes = this.googleMapsService.getNextCalendarEventRoute(lat, lng,
					key);

			if (routes.length <= 0 || routes[0].legs.length <= 0)
				throw new Exception();

		} catch (Exception e) {
			return "No route available.";
		}

		DateTime routDepartureTime = routes[0].legs[0].departureTime;

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -5);
		DateTime now = new DateTime(cal.getTime());

		if (now.compareTo(routDepartureTime) >= 0)
			return "Si";

		return "No";
	}

	@RequestMapping(value = { "/getSteps/{key}" }, method = RequestMethod.GET)
	@ResponseBody
	public DirectionsStep[] getSteps(@PathVariable("key") String key,
			@RequestParam(value = "lat") Double lat,
			@RequestParam(value = "lng") Double lng, Model model) {

		DirectionsRoute[] routes = null;

		try {
			routes = this.googleMapsService.getNextCalendarEventRoute(lat, lng,
					key);

			if (routes.length <= 0 || routes[0].legs.length <= 0
					|| routes[0].legs[0].steps.length <= 0)
				throw new Exception();

		} catch (Exception e) {
			return null;
		}

		return routes[0].legs[0].steps;
	}

	@RequestMapping(value = { "/json/{key}" }, method = RequestMethod.GET)
	@Deprecated
	public String json(@PathVariable("key") String key,
			@RequestParam(value = "lat") Double lat,
			@RequestParam(value = "lng") Double lng, Model model) {

		DirectionsRoute[] routes = null;

		try {
			routes = this.googleMapsService.getNextCalendarEventRoute(lat, lng,
					key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		String routesString = "";
		try {
			routesString = mapper.writeValueAsString(routes);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		model.addAttribute("response", routesString);
		model.addAttribute("origin", new LatLng(lat, lng));
		// model.addAttribute("destination", calendarEvent.getLocation());
		model.addAttribute("travelMode",
				"google.maps.DirectionsTravelMode.TRANSIT");

		return "directions/mapa";
	}
	
	@RequestMapping(value = { "/{key}" }, method = RequestMethod.GET)
	@ResponseBody
	@Deprecated
	public DirectionsRoute[] getRoute(@PathVariable("key") String key,
			@RequestParam(value = "lat") Double lat,
			@RequestParam(value = "lng") Double lng, Model model) {

		DirectionsRoute[] routes = null;

		try {
			routes = this.googleMapsService.getNextCalendarEventRoute(lat, lng,
					key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return routes;
	}
}
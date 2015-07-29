package cl.uchile.transubic.controllers;

import java.io.IOException;
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
import cl.uchile.transubic.service.UserService;
import cl.uchile.transubic.user.model.User;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

@Controller
@RequestMapping(value = { "/direction" })
public class DirectionController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("calendarEventService")
	private CalendarEventService calendarEventService;

	@RequestMapping(value = { "/{key}" }, method = RequestMethod.GET)
	@ResponseBody
	public DirectionsRoute[] getRoute(@PathVariable("key") String key,
			@RequestParam(value = "lat") Double lat,
			@RequestParam(value = "lng") Double lng, Model model) {

		GeoApiContext context = new GeoApiContext()
				.setApiKey("AIzaSyAwL4a_JOLh8XW1E1A2rOadLZNo_x9wfEc");
		DirectionsRoute[] routes = null;

		User user = this.userService.getUserByEncodedKey(key);
		if (user == null)
			return routes;

		CalendarEvent calendarEvent = this.calendarEventService
				.getNextCalendarEventsByUserIdAndDate(user.getUserId(),
						new Date());

		if (calendarEvent == null)
			return routes;

		try {
			routes = DirectionsApi
					.newRequest(context)
					.mode(TravelMode.TRANSIT)
					.units(Unit.METRIC)
					.language("es")
					// .origin("Borgoña, Santiago, Chile")
					// .origin(new LatLng(-33.492304, -70.552308))
					.region("CL")
					.origin(new LatLng(lat, lng))
					.arrivalTime(new DateTime(calendarEvent.getEventDateTime()))
					.destination(
							"Universidad de Chile - Campus Beauchef, Santiago, Chile")
					.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return routes;
	}

	@RequestMapping(value = { "/getMap/{key}" }, method = RequestMethod.GET)
	public String getMap(@PathVariable("key") String key,
			@RequestParam(value = "lat") Double lat,
			@RequestParam(value = "lng") Double lng, Model model) {

		User user = this.userService.getUserByEncodedKey(key);
		if (user == null)
			return "redirect:/";

		CalendarEvent calendarEvent = this.calendarEventService
				.getNextCalendarEventsByUserIdAndDate(user.getUserId(),
						new Date());

		if (calendarEvent == null)
			return "redirect:/";

		model.addAttribute("region", "CL");
		model.addAttribute("language", "es");
		model.addAttribute("origin", new LatLng(lat, lng));
		model.addAttribute("destination", calendarEvent.getLocation());
		model.addAttribute("arrivalTime",
				calendarEvent.getEventDateTime().getTime());
		model.addAttribute("travelMode",
				"google.maps.DirectionsTravelMode.TRANSIT");

		return "directions/mapa";
	}

	@RequestMapping(value = { "/json/{key}" }, method = RequestMethod.GET)
	public String json(@PathVariable("key") String key,
			@RequestParam(value = "lat") Double lat,
			@RequestParam(value = "lng") Double lng, Model model) {

		GeoApiContext context = new GeoApiContext()
				.setApiKey("AIzaSyAwL4a_JOLh8XW1E1A2rOadLZNo_x9wfEc");
		DirectionsRoute[] routes = null;

		User user = this.userService.getUserByEncodedKey(key);
		if (user == null)
			return "redirect:/";

		CalendarEvent calendarEvent = this.calendarEventService
				.getNextCalendarEventsByUserIdAndDate(user.getUserId(),
						new Date());

		if (calendarEvent == null)
			return "redirect:/";

		try {
			routes = DirectionsApi
					.newRequest(context)
					.mode(TravelMode.TRANSIT)
					.units(Unit.METRIC)
					.language("es")
					// .origin("Borgoña, Santiago, Chile")
					// .origin(new LatLng(-33.492304, -70.552308))
					.region("CL")
					.origin(new LatLng(lat, lng))
					.arrivalTime(new DateTime(calendarEvent.getEventDateTime()))
					// .destination(
					// "Universidad de Chile - Campus Beauchef, Santiago, Chile")
					.destination(calendarEvent.getLocation()).await();
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
		model.addAttribute("destination", calendarEvent.getLocation());
		model.addAttribute("travelMode",
				"google.maps.DirectionsTravelMode.TRANSIT");

		return "directions/mapa";
	}
}
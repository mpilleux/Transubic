package cl.uchile.transubic.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cl.uchile.transubic.calendarEvent.json.CalendarEventJson;
import cl.uchile.transubic.service.CalendarEventService;
import cl.uchile.transubic.service.SpringSecurityService;
import cl.uchile.transubic.service.UserService;
import cl.uchile.transubic.user.model.User;

@Controller
public class MainController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("calendarEventService")
	private CalendarEventService calendarEventService;

	@Autowired
	private SpringSecurityService springSecurityService;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String defaultPage(Model model) {

		Integer userId = this.springSecurityService.getSessionEmployeeId();
		List<CalendarEventJson> calendarEvents = this.calendarEventService
				.convertCalendarEventsToJson(this.calendarEventService
						.getAllCalendarEventsByUserId(userId));

		ObjectMapper mapper = new ObjectMapper();
		String events = "";
		try {
			events = mapper.writeValueAsString(calendarEvents);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		model.addAttribute("events", events);
		model.addAttribute("defaultDate", new SimpleDateFormat("yyyy-MM-dd",
				Locale.US).format(new Date()));

		model.addAttribute("breadcrumbP", "Home");
		model.addAttribute("breadcrumbCh", "Home");

		return "calendars/calendar";
	}

	@RequestMapping(value = "/login", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request) {

		if (request.getUserPrincipal() != null
				&& request.getUserPrincipal().getName().length() > 0) {
			return new ModelAndView("redirect:/");
		}

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error",
					getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		model.setViewName("login");

		return model;

	}

	@RequestMapping(value = "/getKey", method = { RequestMethod.GET })
	@ResponseBody
	public String getKey(
			@RequestParam(value = "rut", required = false) String rut,
			@RequestParam(value = "password", required = false) String password,
			HttpServletRequest request) {
		
		System.out.println("rut " + rut);
		System.out.println("password " + password);

		if (rut != null && password != null) {
			User user = this.userService.findByUserRut(rut);

			if (user != null
					&& this.springSecurityService.passwordsMatches(password,
							user.getPassword())) {
				return user.getKey();
			}
		}

		return "";

	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logout(
			@RequestParam(value = "error", required = false) String error,
			HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error",
					getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		model.addObject("msg", "You've been logged out successfully.");

		model.setViewName("login");

		return model;

	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession()
				.getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;

	}

}
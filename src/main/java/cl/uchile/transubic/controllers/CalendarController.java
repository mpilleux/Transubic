package cl.uchile.transubic.controllers;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.uchile.transubic.calendarEvent.form.AgregarEventoForm;
import cl.uchile.transubic.calendarEvent.form.ModificarEventoForm;
import cl.uchile.transubic.calendarEvent.model.CalendarEvent;
import cl.uchile.transubic.service.CalendarEventService;
import cl.uchile.transubic.service.SpringSecurityService;
import cl.uchile.transubic.service.UserService;

@Controller
@RequestMapping(value = { "/calendar" })
public class CalendarController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("calendarEventService")
	private CalendarEventService calendarEventService;

	@Autowired
	private SpringSecurityService springSecurityService;

	@RequestMapping(value = { "/formAgregarEvento" }, method = RequestMethod.POST)
	public String addEventoForm(Model model,
			@Valid AgregarEventoForm agregarEventoForm,
			BindingResult bindingResult) {

		CalendarEvent calendarEvent = new CalendarEvent();
		try {
			calendarEvent.setEventDateString(agregarEventoForm.getDate());
		} catch (ParseException e) {
			return "redirect:/";
		}

		model.addAttribute("action", "agregarEvento");
		model.addAttribute("calendarEvent", calendarEvent);
		model.addAttribute("breadcrumbP", "Calendario");
		model.addAttribute("breadcrumbCh", "Agregar evento");

		return "calendars/calendarEvent";
	}

	@RequestMapping(value = { "/agregarEvento" }, method = RequestMethod.POST)
	public String addEvento(@Valid CalendarEvent calendarEvent,
			BindingResult bindingResult, Model model,
			final RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("action", "agregarEvento");
			model.addAttribute("calendarEvent", calendarEvent);
			model.addAttribute("breadcrumbP", "Calendario");
			model.addAttribute("breadcrumbCh", "Agregar evento");
			return "calendars/calendarEvent";
		}

		this.calendarEventService.addCalendarEvent(calendarEvent);

		redirectAttributes.addFlashAttribute("formMessage",
				"Evento agregado exitosamente.");

		return "redirect:/";
	}

	@RequestMapping(value = { "/formEditarEvento" }, method = RequestMethod.POST)
	public String editEventoForm(Model model,
			ModificarEventoForm modificarEventoForm,
			BindingResult bindingResult) {

		CalendarEvent calendarEvent = this.calendarEventService
				.findByCalendarEventId(modificarEventoForm.getId());

		if (calendarEvent == null
				|| calendarEvent.getUserId() != this.springSecurityService
						.getSessionEmployeeId()) {
			return "redirect:/";
		}

		model.addAttribute("action", "editarEvento");
		model.addAttribute("calendarEvent", calendarEvent);
		model.addAttribute("breadcrumbP", "Calendario");
		model.addAttribute("breadcrumbCh", "Editar evento");

		return "calendars/calendarEvent";
	}

	@RequestMapping(value = { "/editarEvento" }, method = RequestMethod.POST)
	public String editEvento(@Valid CalendarEvent calendarEvent,
			BindingResult bindingResult, Model model,
			final RedirectAttributes redirectAttributes) {

		CalendarEvent calendarEventAux = this.calendarEventService
				.findByCalendarEventId(calendarEvent.getEventId());

		if (calendarEventAux == null
				|| calendarEvent.getUserId() != this.springSecurityService
						.getSessionEmployeeId()
				|| calendarEvent.getUserId() != calendarEventAux.getUserId()) {
			// the user is trying to change someone elses calendar event
			return "redirect:/";
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("action", "editarEvento");
			model.addAttribute("calendarEvent", calendarEvent);
			model.addAttribute("breadcrumbP", "Calendario");
			model.addAttribute("breadcrumbCh", "Editar evento");
			return "calendars/calendarEvent";
		}

		this.calendarEventService.updateCalendarEvent(calendarEvent);

		redirectAttributes.addFlashAttribute("formMessage",
				"Evento editado exitosamente.");

		return "redirect:/";
	}

	@RequestMapping(value = { "/formEliminarEvento" }, method = RequestMethod.POST)
	public String deleteEventoForm(Model model,
			ModificarEventoForm modificarEventoForm,
			BindingResult bindingResult) {

		CalendarEvent calendarEvent = this.calendarEventService
				.findByCalendarEventId(modificarEventoForm.getId());

		if (calendarEvent == null
				|| calendarEvent.getUserId() != this.springSecurityService
						.getSessionEmployeeId()) {
			return "redirect:/";
		}

		model.addAttribute("modificarEventoForm", modificarEventoForm);
		model.addAttribute("breadcrumbP", "Calendario");
		model.addAttribute("breadcrumbCh", "Eliminar evento");

		return "calendars/deleteCalendarEvent";
	}

	@RequestMapping(value = { "/eliminarEvento" }, method = RequestMethod.POST)
	public String deleteEvento(@Valid ModificarEventoForm modificarEventoForm,
			BindingResult bindingResult, Model model,
			final RedirectAttributes redirectAttributes) {

		CalendarEvent calendarEvent = this.calendarEventService
				.findByCalendarEventId(modificarEventoForm.getId());

		if (calendarEvent == null
				|| calendarEvent.getUserId() != this.springSecurityService
						.getSessionEmployeeId()) {
			return "redirect:/";
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("modificarEventoForm", modificarEventoForm);
			model.addAttribute("breadcrumbP", "Calendario");
			model.addAttribute("breadcrumbCh", "Eliminar evento");
			return "calendars/deleteCalendarEvent";
		}

		this.calendarEventService.deleteCalendarEvent(calendarEvent);

		redirectAttributes.addFlashAttribute("formMessage",
				"Evento eliminado exitosamente.");

		return "redirect:/";
	}
}
package cl.uchile.transubic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.uchile.transubic.calendarEvent.dao.CalendarEventDao;
import cl.uchile.transubic.calendarEvent.json.CalendarEventJson;
import cl.uchile.transubic.calendarEvent.model.CalendarEvent;

@Service("calendarEventService")
public class CalendarEventService {

	@Autowired
	private CalendarEventDao calendarEventDao;

	@Autowired
	private SpringSecurityService springSecurityService;

	@Transactional
	public CalendarEvent findByCalendarEventId(Integer calendarEventId) {
		return this.calendarEventDao.findByCalendarEventId(calendarEventId);
	}

	@Transactional
	public List<CalendarEvent> getAllCalendarEventsByUserId(Integer userId) {
		if (userId == null)
			return new ArrayList<CalendarEvent>();

		return this.calendarEventDao.getAllCalendarEventsByUserId(userId);
	}

	@Transactional
	public List<CalendarEvent> getCalendarEventsByUserIdAndDate(Integer userId,
			Date date) {
		if (userId == null || date == null)
			return new ArrayList<CalendarEvent>();

		return this.calendarEventDao.getCalendarEventsByUserIdAndDate(userId,
				date);
	}

	@Transactional
	/**
	 * 
	 * @param userId
	 * @param date
	 * @return the next event in date, but filting events that have already passed
	 */
	public CalendarEvent getNextCalendarEventsByUserIdAndDate(Integer userId,
			Date date) {
		List<CalendarEvent> calendarEventList = this
				.getCalendarEventsByUserIdAndDate(userId, date);
		CalendarEvent calendarEventResult = null;

		for (CalendarEvent calendarEvent : calendarEventList) {
			if (calendarEvent.getEventDateTime().compareTo(date) >= 0) {
				calendarEventResult = calendarEvent;
				break;
			}
		}

		return calendarEventResult;

	}

	@Transactional
	public List<CalendarEventJson> convertCalendarEventsToJson(
			List<CalendarEvent> calendarEventList) {
		List<CalendarEventJson> calendarEventJsonList = new ArrayList<CalendarEventJson>();

		if (calendarEventList != null)
			for (CalendarEvent calendarEvent : calendarEventList)
				calendarEventJsonList.add(new CalendarEventJson(calendarEvent));

		return calendarEventJsonList;
	}

	@Transactional
	public void addCalendarEvent(CalendarEvent calendarEvent) {
		calendarEvent.setUserId(this.springSecurityService
				.getSessionEmployeeId());
		this.calendarEventDao.addCalendarEvent(calendarEvent);
	}

	@Transactional
	public void updateCalendarEvent(CalendarEvent calendarEvent) {
		calendarEvent.setUserId(this.springSecurityService
				.getSessionEmployeeId());
		this.calendarEventDao.updateCalendarEvent(calendarEvent);
	}

	@Transactional
	public void deleteCalendarEvent(CalendarEvent calendarEvent) {
		this.calendarEventDao.deleteCalendarEvent(calendarEvent);
	}
}

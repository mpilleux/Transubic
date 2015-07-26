package cl.uchile.transubic.calendarEvent.dao;

import java.util.List;

import cl.uchile.transubic.calendarEvent.model.CalendarEvent;

public interface CalendarEventDao {

	public CalendarEvent findByCalendarEventId(Integer employeeId);

	public List<CalendarEvent> getAllCalendarEventsByUserId(Integer userId);

	public void addCalendarEvent(CalendarEvent calendarEvent);

	public void updateCalendarEvent(CalendarEvent calendarEvent);

	public void deleteCalendarEvent(CalendarEvent calendarEvent);

}

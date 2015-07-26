package cl.uchile.transubic.calendarEvent.json;

import java.text.SimpleDateFormat;
import java.util.Locale;

import cl.uchile.transubic.calendarEvent.model.CalendarEvent;

public class CalendarEventJson {

	private Integer id;
	private String title;
	private String start;// : '2015-02-16T16:00:00'
	private String location;

	public CalendarEventJson() {
	}

	public CalendarEventJson(CalendarEvent calendarEvent) {
		this.setId(calendarEvent.getEventId());
		this.setTitle(calendarEvent.getTitle());
		this.setLocation(calendarEvent.getLocation());
		
		String date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendarEvent
				.getEventDate());
		date+="T";
		date+=new SimpleDateFormat("HH:mm:ss", Locale.US).format(calendarEvent
				.getEventTime());
		this.setStart(date);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}

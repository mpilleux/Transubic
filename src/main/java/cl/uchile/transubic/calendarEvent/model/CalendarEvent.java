package cl.uchile.transubic.calendarEvent.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import cl.uchile.transubic.user.model.User;

@Entity
@Table(name = "calendar_event")
public class CalendarEvent {

	private Integer eventId;
	private Date eventDate;
	private Date eventTime;
	private Integer userId;
	private User user;
	private String title;
	private String location;

	private String eventDateString;
	private String eventTimeString;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "C_EVE_ID", unique = true, nullable = false)
	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "C_EVE_DATE", nullable = false)
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;

		if (this.getEventDateString() == null) {
			this.eventDateString = new SimpleDateFormat("yyyy-MM-dd", Locale.US)
					.format(this.getEventDate());
		}
	}

	@NotNull
	@Temporal(TemporalType.TIME)
	@Column(name = "C_EVE_TIME", nullable = false)
	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;

		if (this.getEventTimeString() == null) {
			this.eventTimeString = new SimpleDateFormat("hh:mm", Locale.US)
					.format(this.getEventTime());
		}
	}

	@Column(name = "C_EVE_USE_ID")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@NotNull
	@Length(min = 1, max = 128)
	@Column(name = "C_EVE_LOCATION", nullable = false, length = 128)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@NotNull
	@Length(min = 1, max = 128)
	@Column(name = "C_EVE_TITLE", nullable = false, length = 128)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "C_EVE_USE_ID", updatable = false, insertable = false, referencedColumnName = "USE_ID")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Transient
	public String getEventDateFormatted() {
		if (this.getEventDate() == null)
			return "";

		return new SimpleDateFormat("dd-MMM-yyyy", Locale.US).format(this
				.getEventDate());
	}

	@Transient
	public String getEventDateString() {
		return eventDateString;
	}

	public void setEventDateString(String eventDateString)
			throws ParseException {
		this.eventDateString = eventDateString;
		String expectedPattern = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
		Date date = formatter.parse(eventDateString);
		this.setEventDate(date);
	}

	@Transient
	public String getEventTimeString() {
		return eventTimeString;
	}

	public void setEventTimeString(String eventTimeString)
			throws ParseException {
		this.eventTimeString = eventTimeString;
		String expectedPattern = "hh:mm";
		SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
		Date time = formatter.parse(eventTimeString);
		this.setEventTime(time);
	}

}

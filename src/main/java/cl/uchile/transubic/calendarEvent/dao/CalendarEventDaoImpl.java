package cl.uchile.transubic.calendarEvent.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import cl.uchile.transubic.calendarEvent.model.CalendarEvent;

@Repository
public class CalendarEventDaoImpl implements CalendarEventDao {

	@Autowired
	@Qualifier("sessionFactoryTransubic")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public CalendarEvent findByCalendarEventId(Integer eventId) {
		List<CalendarEvent> users = new ArrayList<CalendarEvent>();

		users = sessionFactory.getCurrentSession()
				.createQuery("from CalendarEvent where eventId=?")
				.setParameter(0, eventId).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CalendarEvent> getAllCalendarEventsByUserId(Integer userId) {
		List<CalendarEvent> calendarEvents = new ArrayList<CalendarEvent>();

		calendarEvents = sessionFactory.getCurrentSession()
				.createQuery("from CalendarEvent where userId= :userId")
				.setParameter("userId", userId).list();

		return calendarEvents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CalendarEvent> getCalendarEventsByUserIdAndDate(Integer userId,
			Date date) {
		List<CalendarEvent> calendarEvents = new ArrayList<CalendarEvent>();

		calendarEvents = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from CalendarEvent where userId= :userId "
								+ "and eventDate = :eventDate "
								+ "order by eventTime")
				.setParameter("userId", userId).setParameter("eventDate", date)
				.list();

		return calendarEvents;
	}

	@Override
	public void addCalendarEvent(CalendarEvent calendarEvent) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(calendarEvent);
	}

	@Override
	public void updateCalendarEvent(CalendarEvent calendarEvent) {
		Session session = sessionFactory.getCurrentSession();
		session.update(calendarEvent);
	}

	@Override
	public void deleteCalendarEvent(CalendarEvent calendarEvent) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(calendarEvent);
	}

}

package cl.uchile.transubic.user.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import cl.uchile.transubic.user.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	@Qualifier("sessionFactoryTransubic")
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	/**
	 * used in login. DO NOT CACHE. Password attribute is required (might change)
	 */
	public User findByUserRut(String rut) {

		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession()
				.createQuery("from User where rut=?").setParameter(0, rut)
				.list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {

		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession().createQuery("from User")
				.list();

		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsersInIdList(List<Integer> idList) {
		// String idQuery = String.join(", ", idList );

		List<User> users = new ArrayList<User>();

		if (idList.size() > 0) {

			users = sessionFactory.getCurrentSession()
					.createQuery("from User where userId in (:ids)")
					.setParameterList("ids", idList).list();
		}

		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findByUserId(Integer userId) {
		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession()
				.createQuery("from User where userId=?")
				.setParameter(0, userId).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void addUser(User nonEmployeeUser) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(nonEmployeeUser);
	}

	@Override
	public void updateUser(User nonEmployeeUser) {
		Session session = sessionFactory.getCurrentSession();
		session.update(nonEmployeeUser);
	}

	@Override
	public void deleteUser(User nonEmployeeUser) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(nonEmployeeUser);
	}

}

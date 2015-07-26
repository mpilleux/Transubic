package cl.uchile.transubic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.uchile.transubic.user.dao.UserDao;
import cl.uchile.transubic.user.model.User;

@Service("userService")
public class UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
	public User findByUserRut(String username) {
		return this.userDao.findByUserRut(username);
	}

	@Transactional
	public User findByUserId(Integer employee_id) {
		return this.userDao.findByUserId(employee_id);
	}

	@Transactional
	public List<User> getAllValidUsers() {
		List<User> userList = this.userDao.getAllUsers();

		if (userList != null) {
			List<User> result = new ArrayList<User>();
			for (User user : userList)
				if (user.isEnabled())
					result.add(user);
			return result;
		}

		return new ArrayList<User>();
	}

	@Transactional
	public List<User> getAllUsersInIdList(List<Integer> idList) {
		return this.userDao.getAllUsersInIdList(idList);
	}

	@Transactional
	public void addUser(User user) {
		user.setCreationDate(new Date());
		user.setEnabled(true);
		user.encryptPassword();
		this.userDao.addUser(user);
	}

	@Transactional
	public void updateUser(User user) {
		this.userDao.updateUser(user);
	}

	@Transactional
	public void deleteUser(User user) {
		user.setEnabled(false);
		this.updateUser(user);
	}
}

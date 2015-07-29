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
	public User findByUserId(Integer userId) {
		return this.userDao.findByUserId(userId);
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
		user.setKeyDate(new Date());
		user.generateDigit();
		user.generateKey();
		this.userDao.addUser(user);
		user.generateKey();
		this.updateUser(user);
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

	@Transactional
	public Integer getUserIdFromHash(String hash) {

		if (hash.length() < 60)
			return 0;

		String subString = hash.substring(33);
		String integers = "123456789";
		String id = "";

		for (int i = 0; i < subString.length(); i++) {
			char c = subString.charAt(i);

			if (!integers.contains(c + ""))
				break;

			id += c;
		}

		if (id.length() > 0)
			return Integer.parseInt(id);

		return 0;
	}

	@Transactional
	public User getUserByEncodedKey(String encodedKey) {
		encodedKey = User.decodeHashUrl(encodedKey);
		Integer userId = this.getUserIdFromHash(encodedKey);

		if (userId <= 0)
			return null;

		User user = this.findByUserId(userId);
		
		if (user == null || !user.isValidHash(encodedKey))
			return null;
		
		return user;
	}

}

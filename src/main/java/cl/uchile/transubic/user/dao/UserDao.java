package cl.uchile.transubic.user.dao;

import java.util.List;

import cl.uchile.transubic.user.model.User;

public interface UserDao {

	User findByUserRut(String username);

	User findByUserId(Integer employeeId);

	List<User> getAllUsers();

	List<User> getAllUsersInIdList(List<Integer> idList);

	public void addUser(User user);

	public void updateUser(User user);

	public void deleteUser(User user);

}

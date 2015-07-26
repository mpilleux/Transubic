package cl.uchile.transubic.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.uchile.transubic.user.dao.UserDao;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	// get user from the database, via Hibernate
	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String rut)
			throws UsernameNotFoundException {

		cl.uchile.transubic.user.model.User user = this.userDao
				.findByUserRut(rut);

		List<GrantedAuthority> authorities = buildUserAuthority(user
				.getUserId());

		return buildUserForAuthentication(user, authorities);
	}

	// Converts com.aui.commuting.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(
			cl.uchile.transubic.user.model.User user,
			List<GrantedAuthority> authorities) {
		return new User(user.getUserId() + "", user.getPassword(),
				user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Integer userId) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));

		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(
				setAuths);

		return result;
	}

}

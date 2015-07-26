package cl.uchile.transubic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("springSecurityService")
public class SpringSecurityService {

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	public Integer getSessionEmployeeId() {
		return Integer
				.parseInt(((UserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal()).getUsername());
	}

	public boolean passwordsMatches(String rawPassword, String encodedPassword) {
		return this.passwordEncoder.matches(rawPassword, encodedPassword);
	}
}

package cl.uchile.transubic.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service("springSecurityService")
public class SpringSecurityService {

	public Integer getSessionEmployeeId() {
		return Integer
				.parseInt(((UserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal()).getUsername());
	}
}

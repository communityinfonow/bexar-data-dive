package org.cinow.omh.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class UserService extends OidcUserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public OidcUser loadUser(OidcUserRequest request) {
		OidcUser oidcUser = super.loadUser(request);
		Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
		oidcUser.getAuthorities().forEach(auth -> mappedAuthorities.add(auth));
		if (this.userRepository.userExists(oidcUser.getAttribute("email"))) {
			mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_bdd-admin"));
		}

		return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
	}
}

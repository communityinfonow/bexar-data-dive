package org.cinow.omh;

import org.cinow.omh.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	@Value("${login.success.url}")
	String loginSuccessUrl;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
        http
			.cors()
			.and()
            .authorizeRequests(a -> a
                .antMatchers("/api/admin/**").access("hasRole('bdd-admin')")
                .anyRequest().permitAll()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
			.logout(l -> l
				.logoutSuccessUrl("/").permitAll()
			)
			.csrf(c -> c
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			)
            .oauth2Login()
			.defaultSuccessUrl(loginSuccessUrl)
			.userInfoEndpoint().oidcUserService(this.userService);
        // @formatter:on
	}
}

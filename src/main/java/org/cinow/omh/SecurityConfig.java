package org.cinow.omh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cinow.omh.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	UserService userService;

	@Value("${login.success.url}")
	String loginSuccessUrl;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
        http
			.cors().and()
			.csrf().disable()
			.headers().frameOptions().disable()
			.and()
            .authorizeRequests(a -> a
                .antMatchers("/admin", "/admin/**", "/api/admin/**").access("hasRole('bdd-admin')")
                .anyRequest().permitAll()
            )
            .exceptionHandling(e -> e
                // custom 403 handler to redirect to the vue unauthorized route
				.accessDeniedHandler(new AccessDeniedHandlerImpl() {
					public void handle(HttpServletRequest request, HttpServletResponse response,
						AccessDeniedException accessDeniedException) throws IOException, ServletException {

							response.sendRedirect("/unauthorized");
					}
				})
				.defaultAuthenticationEntryPointFor(
					new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
					new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest")
				)
            )
			.logout(l -> l
				.logoutSuccessUrl("/").permitAll()
			)
            .oauth2Login()
			.defaultSuccessUrl(loginSuccessUrl)
			.userInfoEndpoint().oidcUserService(this.userService);
        // @formatter:on

		return http.build();
	}
}

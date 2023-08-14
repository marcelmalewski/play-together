package com.marcel.malewski.playtogetherapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

//TODO dodać profile
//TODO co to dokladnie stateless session
//TODO jaki powinien byc dostep do dokumentacji
//TODO obargnac sesjie co to znaczy
//			.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

	//TODO czy swagger powinien być publicznie dostępny?
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.authorizeHttpRequests()

			.requestMatchers(
				HttpMethod.POST,
				"/v1/registration/gamers"
			)
			.permitAll()

			.requestMatchers(
				HttpMethod.GET,
				"/v1/game-sessions",//TODO temp for tests
				"/docs",
				"/v2/api-docs/**",
				"/v3/api-docs/**",
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/swagger-ui.html"
			)
			.permitAll()

			.requestMatchers(
				HttpMethod.DELETE,
				"/v1/gamers/@me"
			)
			.permitAll()

			.requestMatchers(
				"/error"
			)
			.permitAll()

			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.successHandler((request, response, authentication) -> {
				// Do nothing upon successful login
			})
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
			.and()
			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
		return new CustomAccessDeniedHandler();
	}
}

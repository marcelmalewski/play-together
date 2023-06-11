package com.marcel.malewski.playtogetherapi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//TODO dodać profile
//TODO co to dokladnie stateless session
//TODO jaki powinien byc dostep do dokumentacji
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.requestMatchers(
				"/",
				"/v1/persons2",
				"/docs",
				"/v2/api-docs/**",
				"/v3/api-docs/**",
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/swagger-ui.html"
			)
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.and();
//			.exceptionHandling()
//			.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

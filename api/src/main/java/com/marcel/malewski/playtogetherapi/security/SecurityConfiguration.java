package com.marcel.malewski.playtogetherapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
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

	//TODO dodac specjalna permisje dostepu do swaggera
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(
					HttpMethod.POST,
					"/v1/registration/gamers"
				)
				.permitAll()

				.requestMatchers(
					HttpMethod.GET,
					"/docs",
					"/v2/api-docs/**",
					"/v3/api-docs/**",
					"/swagger-resources/**",
					"/swagger-ui/**",
					"/swagger-ui.html"
				)
				.permitAll()

				.requestMatchers(
					"/error",
					"/v1/gamers/@me/authentication-data"
				)
				.permitAll()

				.anyRequest()
				.authenticated()
			)

			.formLogin(formLogin -> formLogin
				.successHandler((request, response, authentication) -> {
					// Do nothing upon successful login
				})
			)

			.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				.accessDeniedHandler(accessDeniedHandler())
			);

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		String hierarchy = """
			GAMER_ANALYSE_PRIVILEGE > GAMER_VIEW_PRIVILEGE
			GAMER_MANAGE_PRIVILEGE > GAMER_ANALYSE_PRIVILEGE
			GAMER_MANAGE_PRIVILEGE > GAMER_CREATE_PRIVILEGE
			GAMER_MANAGE_PRIVILEGE > GAMER_EDIT_PRIVILEGE
			GAMER_MANAGE_PRIVILEGE > GAMER_DELETE_PRIVILEGE
			""";
		roleHierarchy.setHierarchy(hierarchy);
		return roleHierarchy;
	}

	@Bean
	public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler() {
		DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy());
		return expressionHandler;
	}
}

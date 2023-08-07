package com.marcel.malewski.playtogetherapi.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	public static final Logger LOG = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException {
		Authentication auth = SecurityContextHolder.getContext()
			.getAuthentication();
		if (auth != null) {
			LOG.warn("User: " + auth.getName() + " attempted to access the protected URL: " + request.getRequestURI());
			LOG.warn("User roles: " + Arrays.toString(new Collection[]{auth.getAuthorities()}));
		}

		//TODO dodać wypisywanie wymaganych ról
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	}
}

package com.marcel.malewski.playtogetherapi.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

public final class Security {
	private Security() {
	}

	//TODO jakoś obsłyć nulla w request albo response czy tylko warning od notnull
	public static void LogoutManually(@NotNull HttpServletRequest request,
	                                  @NotNull HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
		securityContextLogoutHandler.logout(request, response, auth);
	}
}

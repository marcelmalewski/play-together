package com.marcel.malewski.playtogetherapi.util;

import jakarta.validation.constraints.NotNull;

import java.security.Principal;

//TODO moge zrobić z tego bean, żeby @NotNull działało?
public final class PrincipalExtractor {
	private PrincipalExtractor() {
	}

	//TODO obsłyżyć jakoś tego nulla czy tylko warning od notnull
	public static long extractIdFromPrincipal(@NotNull Principal principal) {
		String gamerIdAsString = principal.getName();
		return Long.parseLong(gamerIdAsString);
	}
}

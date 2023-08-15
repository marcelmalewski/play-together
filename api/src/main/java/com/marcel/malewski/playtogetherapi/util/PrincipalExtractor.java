package com.marcel.malewski.playtogetherapi.util;

import jakarta.validation.constraints.NotNull;

import java.security.Principal;

public final class PrincipalExtractor {
	private PrincipalExtractor() {
	}

	public static long extractGamerIdFromPrincipal(@NotNull Principal principal) {
		String gamerIdAsString = principal.getName();
		return Long.parseLong(gamerIdAsString);
	}
}

package com.marcel.malewski.playtogetherapi.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyAuthenticatedGamerException extends RuntimeException {
	public AlreadyAuthenticatedGamerException() {
		super("Gamer is already authenticated");
	}
}


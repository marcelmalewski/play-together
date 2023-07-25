package com.marcel.malewski.playtogetherapi.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyAuthenticatedUserException extends RuntimeException {
	public AlreadyAuthenticatedUserException() {
		super("User is already authenticated");
	}
}


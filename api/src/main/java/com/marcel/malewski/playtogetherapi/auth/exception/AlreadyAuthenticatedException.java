package com.marcel.malewski.playtogetherapi.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO lepiej nazwac?
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyAuthenticatedException extends RuntimeException {
	public AlreadyAuthenticatedException() {
		super("User is already authenticated");
	}
}


package com.marcel.malewski.playtogetherapi.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GivenPlatformDoesNotExistException extends RuntimeException {
	public GivenPlatformDoesNotExistException(Long platformId) {
		super("Given platform does not exist, platformId: " + platformId);
	}
}

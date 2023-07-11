package com.marcel.malewski.playtogetherapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO co zmienia brak public
@ResponseStatus(value = HttpStatus.NOT_FOUND)
class GamerNotFoundException extends RuntimeException {
	public GamerNotFoundException(Long id) {
		super("Gamer not found exception");
	}
}

//TODO lepiej nazwac?
@ResponseStatus(value = HttpStatus.NOT_FOUND)
class AlreadyAuthenticatedException extends RuntimeException {
	public AlreadyAuthenticatedException(Long id) {
		super("User is already authenticated");
	}
}


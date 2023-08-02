package com.marcel.malewski.playtogetherapi.entity.gamer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailAlreadyUsedException extends RuntimeException {
	public EmailAlreadyUsedException(String email) {
		super("Another gamer already uses this email, email: " + email);
	}
}

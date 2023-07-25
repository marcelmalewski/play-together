package com.marcel.malewski.playtogetherapi.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoginAlreadyUsedException extends RuntimeException {
	public LoginAlreadyUsedException(String login) {
		super("Another user already uses this login, login: " + login);
	}
}

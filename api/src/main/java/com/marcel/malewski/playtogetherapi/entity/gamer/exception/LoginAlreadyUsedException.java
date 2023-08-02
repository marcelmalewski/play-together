package com.marcel.malewski.playtogetherapi.entity.gamer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoginAlreadyUsedException extends RuntimeException {
	public LoginAlreadyUsedException(String login) {
		super("Another gamer already uses this login, login: " + login);
	}
}

package com.marcel.malewski.playtogetherapi.entity.gamer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GamerNotFoundException extends RuntimeException {
	public GamerNotFoundException(Long gamerId) {
		super("Gamer not found, gamer id: " + gamerId);
	}
}

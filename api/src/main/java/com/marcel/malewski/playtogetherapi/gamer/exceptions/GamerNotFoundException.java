package com.marcel.malewski.playtogetherapi.gamer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GamerNotFoundException extends RuntimeException {
	public GamerNotFoundException(Long id) {
		super("Gamer not found exception");
	}
}

package com.marcel.malewski.playtogetherapi.entity.game.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GivenGameDoesNotExistException extends RuntimeException {
	public GivenGameDoesNotExistException(long gameId) {
		super("Given game does not exist, game id: " + gameId);
	}
}

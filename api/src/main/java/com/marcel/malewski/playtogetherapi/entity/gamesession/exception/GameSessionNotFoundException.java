package com.marcel.malewski.playtogetherapi.entity.gamesession.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GameSessionNotFoundException extends RuntimeException {
	public GameSessionNotFoundException(Long gameSessionId) {
		super("Game session not found, game session id: " + gameSessionId);
	}
}

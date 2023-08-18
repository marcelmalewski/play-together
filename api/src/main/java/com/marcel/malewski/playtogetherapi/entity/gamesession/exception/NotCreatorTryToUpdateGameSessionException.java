package com.marcel.malewski.playtogetherapi.entity.gamesession.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotCreatorTryToUpdateGameSessionException extends RuntimeException {
	public NotCreatorTryToUpdateGameSessionException() {
		super("Only the creator can update the game session.");
	}
}

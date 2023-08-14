package com.marcel.malewski.playtogetherapi.entity.gamesession.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidGameSessionSortOptionException extends RuntimeException {
	public InvalidGameSessionSortOptionException(String gameSessionSortOption) {
		super("Invalid game session sort option, sortOption: " + gameSessionSortOption);
	}
}

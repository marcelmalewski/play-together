package com.marcel.malewski.playtogetherapi.exception.sharedexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GivenGameDoesNotExistException extends RuntimeException {
	public GivenGameDoesNotExistException(Long gameId) {
		super("Given game does not exist, gameId: " + gameId);
	}
}

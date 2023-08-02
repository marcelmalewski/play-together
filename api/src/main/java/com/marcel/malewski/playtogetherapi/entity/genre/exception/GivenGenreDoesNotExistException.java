package com.marcel.malewski.playtogetherapi.entity.genre.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GivenGenreDoesNotExistException extends RuntimeException {
	public GivenGenreDoesNotExistException(Long genreId) {
		super("Given genre does not exist, genreId: " + genreId);
	}
}

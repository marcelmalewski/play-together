package com.marcel.malewski.playtogetherapi.entity.gamesession.exception;

import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.GameSessionSortOption;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidGameSessionSortOptionException extends RuntimeException {
	public InvalidGameSessionSortOptionException(String gameSessionSortOption) {
		super(
			"Invalid game session sort option, sort: " + gameSessionSortOption + ". " +
			"Valid values: "	+ Arrays.toString(GameSessionSortOption.values())
		);
	}
}

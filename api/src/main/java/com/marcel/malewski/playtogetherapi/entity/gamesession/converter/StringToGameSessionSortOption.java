package com.marcel.malewski.playtogetherapi.entity.gamesession.converter;

import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.GameSessionSortOption;
import com.marcel.malewski.playtogetherapi.entity.gamesession.exception.InvalidGameSessionSortOptionException;
import org.springframework.core.convert.converter.Converter;

public class StringToGameSessionSortOption implements Converter<String, GameSessionSortOption> {
	@Override
	public GameSessionSortOption convert(String source) {
		try {
			return GameSessionSortOption.valueOf(source.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidGameSessionSortOptionException(source);
		}
	}
}

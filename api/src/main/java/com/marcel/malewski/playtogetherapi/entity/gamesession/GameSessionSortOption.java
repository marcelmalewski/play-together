package com.marcel.malewski.playtogetherapi.entity.gamesession;

import lombok.Getter;
import org.springframework.data.domain.Sort;

import static com.marcel.malewski.playtogetherapi.constants.SortConstants.CREATED_AT_FIELD;
import static com.marcel.malewski.playtogetherapi.constants.SortConstants.DATE_FIELD;

@Getter
public enum GameSessionSortOption {
	DATE_ASC(Sort.by(DATE_FIELD).ascending()),
	DATE_DESC(Sort.by(DATE_FIELD).descending()),
	CREATED_AT_ASC(Sort.by(CREATED_AT_FIELD).ascending()),
	CREATED_AT_DESC(Sort.by(CREATED_AT_FIELD).descending());

	private final Sort sort;

	GameSessionSortOption(Sort sort) {
		this.sort = sort;
	}
}

package com.marcel.malewski.playtogetherapi.entity.gamer;

import lombok.Getter;
import org.springframework.data.domain.Sort;

import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerConstants.LOGIN_FIELD;

@Getter
public enum GamerSortOption {
	LOGIN_ASC(Sort.by(LOGIN_FIELD).ascending()),
	LOGIN_DESC(Sort.by(LOGIN_FIELD).descending());

	private final Sort sort;

	GamerSortOption(Sort sort) {this.sort = sort;}
}

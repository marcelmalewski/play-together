package com.marcel.malewski.playtogetherapi.constants;

import lombok.Getter;
import org.springframework.data.domain.Sort;


//TODO zmienić date i createdAt na consty?
@Getter
public enum GameSessionSort {
	DATE_ASC(Sort.by("date").ascending()),
	DATE_DESC(Sort.by("date").descending()),
	CREATED_AT_ASC(Sort.by("createdAt").ascending()),
	CREATED_AT_DESC(Sort.by("createdAt").descending());

	private final Sort sort;

	GameSessionSort(Sort sort) {
		this.sort = sort;
	}
}

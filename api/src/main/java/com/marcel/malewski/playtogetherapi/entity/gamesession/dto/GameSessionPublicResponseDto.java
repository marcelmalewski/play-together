package com.marcel.malewski.playtogetherapi.entity.gamesession.dto;

import com.marcel.malewski.playtogetherapi.enums.PrivacyLevel;

import java.time.LocalDate;
import java.util.List;

public record GameSessionPublicResponseDto(
	Long id,
	String name,
	boolean isCompetitive,
	PrivacyLevel accessType,
	LocalDate date,
	LocalDate createdAt,
	LocalDate modifiedAt,
	int numberOfMembers,
	int maxMembers,
	int minAge,
	String description,
	String creatorLogin,
	String gameName,
	List<String> platforms,
	boolean isCurrentGamerMember
) {
}

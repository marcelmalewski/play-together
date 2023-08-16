package com.marcel.malewski.playtogetherapi.entity.gamesession.dto;

import com.marcel.malewski.playtogetherapi.enums.PrivacyLevel;

import java.time.LocalDate;
import java.util.List;

public record GameSessionCreateRequestDto(
	String name,
	PrivacyLevel visibilityType,
	boolean isCompetitive,
	PrivacyLevel accessType,
	LocalDate date,
	int maxMembers,
	int minAge,
	String description,
	long gameId,
	List<Long> platformsIds
) {
}

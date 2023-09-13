package com.marcel.malewski.playtogetherapi.entity.gamesession.dto;

import com.marcel.malewski.playtogetherapi.enums.PrivacyLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record GameSessionCreateOrUpdateRequestDto(
	@Size(min = 3, max = 20)
	@NotBlank
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

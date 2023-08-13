package com.marcel.malewski.playtogetherapi.entity.gamesession.dto;

import com.marcel.malewski.playtogetherapi.enums.PrivacyLevelEnum;

import java.time.LocalDate;
import java.util.List;

public record GameSessionResponseDto(
	Long id,
	String name,
	boolean isCompetitive,
	PrivacyLevelEnum accessType,
	LocalDate date,
	int numberOfMembers,
	int maxMembers,
	int minAge,
	boolean isCurrentGamerMember,
	String description,
	String pendingMembers,
	String availabilityTimes,
	String creatorLogin,
	String gameName,
	List<String> platforms
) {
}

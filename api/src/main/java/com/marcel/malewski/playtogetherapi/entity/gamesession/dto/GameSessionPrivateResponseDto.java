package com.marcel.malewski.playtogetherapi.entity.gamesession.dto;

public record GameSessionPrivateResponseDto(
	String pendingMembers,
	String availabilityTimes,
	String members

) {
}

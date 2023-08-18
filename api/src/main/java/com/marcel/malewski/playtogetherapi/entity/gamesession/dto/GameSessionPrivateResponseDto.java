package com.marcel.malewski.playtogetherapi.entity.gamesession.dto;

//TODO rozdzielić to na 3 pobierania
public record GameSessionPrivateResponseDto(
	String pendingMembers,
	String availabilityTimes,
	String members
) {
}

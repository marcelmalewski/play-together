package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import java.time.LocalTime;
import java.util.List;

public record GamerPublicResponseDto(
	Long id,
	String login,
	int age,
	String bio,
	String avatarUrl,
	LocalTime playingTimeStart,
	LocalTime playingTimeEnd,
	List<String> platforms,
	List<String> favouriteGames,
	List<String> favouriteGenres
) {
}

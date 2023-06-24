package com.marcel.malewski.playtogetherapi.gamer.dto;

import java.time.LocalTime;
import java.util.List;

public record GamerPublicResponseDto(
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

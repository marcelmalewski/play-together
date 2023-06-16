package com.marcel.malewski.playtogetherapi.gamer.dto;

import com.marcel.malewski.playtogetherapi.genre.Genre;
import com.marcel.malewski.playtogetherapi.shared.PlatformEnum;

import java.time.LocalTime;
import java.util.List;

public record GamerPublicResponseDto(
	String login,
	int age,
	String bio,
	String avatarUrl,
	LocalTime playingTimeStart,
	LocalTime playingTimeEnd,
	List<PlatformEnum> platformEnums,
	List<String> favouriteGames,
	List<Genre> favouriteGenres
) {
}

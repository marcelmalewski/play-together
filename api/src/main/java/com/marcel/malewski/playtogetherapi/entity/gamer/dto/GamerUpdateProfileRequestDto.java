package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GamerUpdateProfileRequestDto(
	String login,
	LocalDate birthdate,
	String bio,
	String avatarUrl,
	LocalTime playingTimeStart,
	LocalTime playingTimeEnd,
	List<Long> platforms,
	List<Long> favouriteGames,
	List<Long> favouriteGenres
) {
}

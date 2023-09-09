package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GamerPrivateResponseDto(
	Long id,
	String login,
	String email,
	LocalDate birthdate,
	String bio,
	String avatarUrl,
	LocalTime playingTimeStart,
	LocalTime playingTimeEnd,
	List<String> platformsNames,
	List<String> favouriteGamesNames,
	List<String> favouriteGenresNames
) {
}

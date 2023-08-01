package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//TODO dodać walidacje
//TODO bio i avatarUrl może być nullem, ale nie może być pustym stringiem
public record GamerUpdateProfileRequestDto(
	String login,
	LocalDate birthdate,
	String bio,
	String avatarUrl,
	LocalTime playingTimeStart,
	LocalTime playingTimeEnd,
	List<Long> platformsIds,
	List<Long> favouriteGamesIds,
	List<Long> favouriteGenresIds
) {
}

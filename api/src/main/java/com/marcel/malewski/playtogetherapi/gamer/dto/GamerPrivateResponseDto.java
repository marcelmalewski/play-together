package com.marcel.malewski.playtogetherapi.gamer.dto;

import com.marcel.malewski.playtogetherapi.game.Game;
import com.marcel.malewski.playtogetherapi.genre.Genre;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GamerPrivateResponseDto(
	String login,
	String email,
	LocalDate birthDate,
	String bio,
	String avatarUrl,
	LocalTime playingTimeStart,
	LocalTime playingTimeEnd,
	List<String> platforms,
	List<Game> favouriteGames,
	List<Genre> favouriteGenres
) {
}

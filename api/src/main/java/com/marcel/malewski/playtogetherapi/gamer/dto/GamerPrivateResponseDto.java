package com.marcel.malewski.playtogetherapi.gamer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record GamerPrivateResponseDto(
	@NotBlank
	String login,
	@Email
	String email,
	String birthdate,
	@NotBlank
	String bio,
	String avatarUrl,
	@NotBlank
	String playingTimeStart,
	@NotBlank
	String playingTimeEnd,
	String platforms,
	String favouriteGames,
	String favouriteGenres
) {
}

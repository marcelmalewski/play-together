package com.marcel.malewski.playtogetherapi.gamer.dto;

import com.marcel.malewski.playtogetherapi.shared.Platform;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GamerRegisterRequestDto(
	String login,
	String password,
	String email,
	LocalDate birthDate,
	LocalTime playingTimeStart,
	LocalTime playingTimeEnd,
	List<Platform> platforms
) {
}

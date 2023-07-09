package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.validation.ValidatePlayingTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//TODO dodać jakies wieksze wymagania co do hasla
//TODO dodac walidacje platforms size i message
@ValidatePlayingTime
public record GamerRegisterRequestDto(
	@Size(min = 3, max = 20)
	String login,
	@Size(min = 8, max = 20)
	@NotNull
	String password,
	@Email
	@NotNull
	String email,
	@PastOrPresent
	@NotNull
	LocalDate birthDate,
	@NotNull
	LocalTime playingTimeStart,
	@NotNull
	LocalTime playingTimeEnd,
	List<Long> platforms
) {
}

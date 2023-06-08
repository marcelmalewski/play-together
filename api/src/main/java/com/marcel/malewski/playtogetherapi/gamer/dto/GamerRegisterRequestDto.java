package com.marcel.malewski.playtogetherapi.gamer.dto;

import com.marcel.malewski.playtogetherapi.shared.Platform;
import com.marcel.malewski.playtogetherapi.validation.ValidatePlayingTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//TODO jakies wieksze wymagania co do hasla
//ewenutalnie zakomentowac zeby sie latwiej testowalo rejestracje albo nie uzywac @Valid
@ValidatePlayingTime
public record GamerRegisterRequestDto(
	@Size(min=3, max=20)
	String login,
	@Size(min=8, max=20)
	String password,
	@Email
	String email,
	@PastOrPresent
	LocalDate birthDate,

	LocalTime playingTimeStart,
	LocalTime playingTimeEnd,
	@UniqueElements
	List<Platform> platforms
) {
}

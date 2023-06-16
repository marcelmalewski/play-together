package com.marcel.malewski.playtogetherapi.gamer.dto;

import com.marcel.malewski.playtogetherapi.shared.PlatformEnum;
import com.marcel.malewski.playtogetherapi.validation.ValidatePlayingTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//TODO jakies wieksze wymagania co do hasla
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
	@UniqueElements
	@Size(min = 1)
	@NotNull
	List<PlatformEnum> platformEnums
) {
}

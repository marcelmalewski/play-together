package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.validation.ValidatePlayingTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//TODO dodać jakies wieksze wymagania co do hasla
//TODO customowa walidacja formatu dat
@ValidatePlayingTime
public record GamerRegisterRequestDto(
	@Size(min = 3, max = 20)
	@NotNull
	String login,
	@Size(min = 8, max = 20)
	@NotNull
	String password,
	@Email
	String email,
	@PastOrPresent
	LocalDate birthDate,
	LocalTime playingTimeStart,
	LocalTime playingTimeEnd,
	@Size(min = 1, message = "You have to add at least one platform")
	@NotNull
	@UniqueElements(message = "Must only contain unique platforms")
	List<Long> platforms
) {
}

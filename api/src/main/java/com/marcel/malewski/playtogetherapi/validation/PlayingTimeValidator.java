package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerRegisterRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class PlayingTimeValidator implements ConstraintValidator<ValidatePlayingTime, GamerRegisterRequestDto> {
	@Override
	public void initialize(ValidatePlayingTime constraintAnnotation) {}

	@Override
	public boolean isValid(GamerRegisterRequestDto requestDto, ConstraintValidatorContext context) {
		LocalTime playingTimeStart = requestDto.playingTimeStart();
		LocalTime playingTimeEnd = requestDto.playingTimeEnd();

		// Perform the validation
		if (playingTimeStart != null && playingTimeEnd != null) {
			return playingTimeEnd.isAfter(playingTimeStart);
		}

		return true;
	}
}

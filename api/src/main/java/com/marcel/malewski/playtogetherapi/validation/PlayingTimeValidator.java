package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.auth.register.GamerRegisterRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class PlayingTimeValidator implements ConstraintValidator<ValidatePlayingTime, GamerRegisterRequestDto> {
	@Override
	public boolean isValid(GamerRegisterRequestDto requestDto, ConstraintValidatorContext context) {
		LocalTime playingTimeStart = requestDto.playingTimeStart();
		LocalTime playingTimeEnd = requestDto.playingTimeEnd();

		return playingTimeEnd.isAfter(playingTimeStart);
	}
}

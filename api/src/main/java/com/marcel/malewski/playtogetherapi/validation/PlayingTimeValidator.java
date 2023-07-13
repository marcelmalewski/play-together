package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.auth.register.GamerRegisterRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlayingTimeValidator implements ConstraintValidator<ValidatePlayingTime, GamerRegisterRequestDto> {
	@Override
	public boolean isValid(GamerRegisterRequestDto gamerRegisterRequestDto, ConstraintValidatorContext context) {
//		LocalTime playingTimeStart = gamerRegisterRequestDto.playingTimeStart();
//		LocalTime playingTimeEnd = gamerRegisterRequestDto.playingTimeEnd();
//
//		return playingTimeEnd.isAfter(playingTimeStart);
		return true;
	}
}

package com.marcel.malewski.playtogetherapi.validation.playingtime;

import com.marcel.malewski.playtogetherapi.interfaces.EntityWithPlayingTimeAsString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.util.Optional;

import static com.marcel.malewski.playtogetherapi.validation.DateTimeParser.tryParseToTime;

public class PlayingTimeStringValidator implements ConstraintValidator<ValidPlayingTime, EntityWithPlayingTimeAsString> {
	@Override
	public boolean isValid(EntityWithPlayingTimeAsString gamerRegisterRequestDto, ConstraintValidatorContext context) {
		Optional<LocalTime> optionalPlayingTimeStart = tryParseToTime(gamerRegisterRequestDto.getPlayingTimeStartAsString());
		if(optionalPlayingTimeStart.isEmpty()) {
			return true;
		}

		Optional<LocalTime> optionalPlayingTimeEnd = tryParseToTime(gamerRegisterRequestDto.getPlayingTimeEndAsString());
		return optionalPlayingTimeEnd.map(localTime -> localTime.isAfter(optionalPlayingTimeStart.get())).orElse(true);
	}
}

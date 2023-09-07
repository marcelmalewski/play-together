package com.marcel.malewski.playtogetherapi.validation.playingtime;

import com.marcel.malewski.playtogetherapi.interfaces.EntityWithPlayingTimeAsString;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.TIME_FORMAT;

//TODO sprobować użyć bezpośrednio formatter.parse()
public class PlayingTimeValidator implements ConstraintValidator<ValidPlayingTime, EntityWithPlayingTimeAsString> {
	@Override
	public boolean isValid(EntityWithPlayingTimeAsString gamerRegisterRequestDto, ConstraintValidatorContext context) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);

		String playingTimeStart = gamerRegisterRequestDto.getPlayingTimeStartAsString();
		LocalTime playingTimeStartAsDate;
		try {
			playingTimeStartAsDate = LocalTime.parse(playingTimeStart, timeFormatter);
		} catch (DateTimeParseException | NullPointerException exception) {
			return true;
		}

		String playingTimeEnd = gamerRegisterRequestDto.getPlayingTimeEndAsString();
		LocalTime playingTimeEndAsDate;
		try {
			playingTimeEndAsDate = LocalTime.parse(playingTimeEnd, timeFormatter);
		} catch (DateTimeParseException | NullPointerException exception) {
			return true;
		}

		return playingTimeEndAsDate.isAfter(playingTimeStartAsDate);
	}
}

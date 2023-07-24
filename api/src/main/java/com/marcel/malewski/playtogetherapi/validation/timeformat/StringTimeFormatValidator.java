package com.marcel.malewski.playtogetherapi.validation.timeformat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import static com.marcel.malewski.playtogetherapi.consts.DateConstants.TIME_FORMAT;

public class StringTimeFormatValidator implements ConstraintValidator<ValidateTimeFormat, String>  {
	@Override
	public boolean isValid(String time, ConstraintValidatorContext context) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		if(time == null) {
			return true;
		}

		try {
			timeFormatter.parse(time);
		} catch (DateTimeParseException exception) {
			return false;
		}

		return true;
	}
}

package com.marcel.malewski.playtogetherapi.validation.timeformat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class TimeFormatValidator implements ConstraintValidator<ValidateTimeFormat, String>  {
	@Override
	public boolean isValid(String time, ConstraintValidatorContext context) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);
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

package com.marcel.malewski.playtogetherapi.validation.dateformat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class StringDateFormatValidator implements ConstraintValidator<ValidateDateFormat, String> {
	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.STRICT);
		if(date == null) {
			return true;
		}

		try {
			dateFormatter.parse(date);
		} catch (DateTimeParseException exception) {
			return false;
		}

		return true;
	}
}

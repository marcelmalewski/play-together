package com.marcel.malewski.playtogetherapi.validation.pastorpresent;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import static com.marcel.malewski.playtogetherapi.consts.DateUtils.DATE_FORMAT;

public class StringPastOrPresentValidator implements ConstraintValidator<ValidateStringPastOrPresent, String> {
	@Override
	public boolean isValid(String dateAsString, ConstraintValidatorContext context) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalDate date;
		try {
			date = LocalDate.parse(dateAsString, dateFormatter);
		} catch (DateTimeParseException | NullPointerException exception) {
			return true;
		}

		LocalDate today = LocalDate.now();
		return !date.isAfter(today);
	}
}

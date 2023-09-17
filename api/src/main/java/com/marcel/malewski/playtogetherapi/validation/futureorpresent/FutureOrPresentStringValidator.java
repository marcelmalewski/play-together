package com.marcel.malewski.playtogetherapi.validation.futureorpresent;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.constant.DateConstants.MIN_AGE;

public class FutureOrPresentStringValidator implements ConstraintValidator<FutureOrPresentCustom, String> {
	@Override
	public boolean isValid(String dateAsString, ConstraintValidatorContext context) {
		//TODO duplicate?
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalDate date;
		try {
			date = LocalDate.parse(dateAsString, dateFormatter);
		} catch (DateTimeParseException | NullPointerException exception) {
			return true;
		}

		LocalDate today = LocalDate.now().minusYears(MIN_AGE);
		return !date.isBefore(today);
	}
}

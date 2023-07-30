package com.marcel.malewski.playtogetherapi.validation.minage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import static com.marcel.malewski.playtogetherapi.utils.DateUtils.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.utils.DateUtils.MIN_AGE;

public class MinAgeValidator implements ConstraintValidator<ValidateMinAge, String>{
	@Override
	public boolean isValid(String dateAsString, ConstraintValidatorContext context) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalDate date;
		try {
			date = LocalDate.parse(dateAsString, dateFormatter);
		} catch (DateTimeParseException | NullPointerException exception) {
			return true;
		}

		LocalDate fifteenYearsBeforeToday = LocalDate.now().minusYears(MIN_AGE);
		return !date.isAfter(fifteenYearsBeforeToday);
	}
}

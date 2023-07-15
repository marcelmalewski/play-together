package com.marcel.malewski.playtogetherapi.validation.pastorpresent;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class StringPastOrPresentValidator implements ConstraintValidator<ValidateStringPastOrPresent, String> {
	@Override
	public boolean isValid(String dateAsString, ConstraintValidatorContext context) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.STRICT);
		LocalDate date;
		System.out.println("Yes");
		try {
			date = LocalDate.parse(dateAsString, dateFormatter);
		} catch (DateTimeParseException | NullPointerException exception) {
			return true;
		}

		LocalDate today = LocalDate.now();
		return !today.isAfter(date);
	}
}

package com.marcel.malewski.playtogetherapi.validation.futureorpresent;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Optional;

import static com.marcel.malewski.playtogetherapi.validation.DateTimeParser.tryParseToDate;

public class FutureOrPresentStringValidator implements ConstraintValidator<FutureOrPresentCustom, String> {
	@Override
	public boolean isValid(String dateAsString, ConstraintValidatorContext context) {
		Optional<LocalDate> optionalDate = tryParseToDate(dateAsString);
		if (optionalDate.isEmpty()) {
			return true;
		}

		LocalDate date = optionalDate.get();
		LocalDate today = LocalDate.now();
		return !date.isBefore(today);
	}
}

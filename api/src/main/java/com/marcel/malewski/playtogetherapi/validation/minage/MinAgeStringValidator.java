package com.marcel.malewski.playtogetherapi.validation.minage;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Optional;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.MIN_AGE;
import static com.marcel.malewski.playtogetherapi.validation.DateTimeParser.tryParseToDate;

public class MinAgeStringValidator implements ConstraintValidator<ValidMinAge, String> {
	@Override
	public boolean isValid(String dateAsString, ConstraintValidatorContext context) {
		Optional<LocalDate> optionalDate = tryParseToDate(dateAsString);
		if (optionalDate.isEmpty()) {
			return true;
		}

		LocalDate date = optionalDate.get();
		LocalDate fifteenYearsBeforeToday = LocalDate.now().minusYears(MIN_AGE);
		return !date.isAfter(fifteenYearsBeforeToday);
	}
}

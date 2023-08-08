package com.marcel.malewski.playtogetherapi.validation.dateformat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.marcel.malewski.playtogetherapi.constants.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.utils.DateTimeValidator.ValidateDateTimeFormat;

public class StringDateFormatValidator implements ConstraintValidator<ValidateDateFormat, String> {
	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {
		return ValidateDateTimeFormat(date, DATE_FORMAT);
	}
}

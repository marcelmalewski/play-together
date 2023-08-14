package com.marcel.malewski.playtogetherapi.validation.timeformat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.marcel.malewski.playtogetherapi.constants.DateConstants.TIME_FORMAT;
import static com.marcel.malewski.playtogetherapi.util.DateTimeValidator.ValidateDateTimeFormat;

public class StringTimeFormatValidator implements ConstraintValidator<ValidateTimeFormat, String>  {
	@Override
	public boolean isValid(String time, ConstraintValidatorContext context) {
		return ValidateDateTimeFormat(time, TIME_FORMAT);
	}
}

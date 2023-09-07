package com.marcel.malewski.playtogetherapi.validation.timeformat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.TIME_FORMAT;
import static com.marcel.malewski.playtogetherapi.validation.Validator.ValidateDateTimeFormat;

public class StringTimeFormatValidator implements ConstraintValidator<ValidTimeFormat, String>  {
	@Override
	public boolean isValid(String time, ConstraintValidatorContext context) {
		return ValidateDateTimeFormat(time, TIME_FORMAT);
	}
}

package com.marcel.malewski.playtogetherapi.validation.timeformat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.marcel.malewski.playtogetherapi.consts.DateUtils.TIME_FORMAT;
import static com.marcel.malewski.playtogetherapi.consts.DateUtils.ValidateDateTimeFormat;

public class StringTimeFormatValidator implements ConstraintValidator<ValidateTimeFormat, String>  {
	@Override
	public boolean isValid(String time, ConstraintValidatorContext context) {
		return ValidateDateTimeFormat(time, TIME_FORMAT);
	}
}

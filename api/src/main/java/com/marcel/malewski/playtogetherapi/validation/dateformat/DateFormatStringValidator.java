package com.marcel.malewski.playtogetherapi.validation.dateformat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.DATE_FORMAT;
import static com.marcel.malewski.playtogetherapi.validation.Validator.ValidateDateTimeFormat;

public class DateFormatStringValidator implements ConstraintValidator<ValidDateFormat, String> {
	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {
		return ValidateDateTimeFormat(date, DATE_FORMAT);
	}
}

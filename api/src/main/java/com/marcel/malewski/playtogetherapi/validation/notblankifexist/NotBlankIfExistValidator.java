package com.marcel.malewski.playtogetherapi.validation.notblankifexist;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankIfExistValidator implements ConstraintValidator<NotBlankIfExist, String> {
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value == null || !value.isBlank();
	}
}

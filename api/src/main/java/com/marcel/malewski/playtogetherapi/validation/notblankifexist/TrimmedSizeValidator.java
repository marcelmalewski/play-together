package com.marcel.malewski.playtogetherapi.validation.notblankifexist;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TrimmedSizeValidator implements ConstraintValidator<TrimmedSize, String> {
	private TrimmedSize constraint;

	@Override
	public void initialize(TrimmedSize constraint) {
		this.constraint = constraint;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) {
			return true;
		}

		String trimmedValue = value.trim();
		return trimmedValue.length() >= constraint.min() && trimmedValue.length() <= constraint.max();
	}
}

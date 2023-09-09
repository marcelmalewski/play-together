package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.testObject.TrimmedSizeTestObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora i wtedy dodac test z nullami
public class TrimmedSizeValidatorTest {
	private Validator validator;
	private TrimmedSizeTestObject trimmedSizeTestObject;

	@BeforeEach
	void setup() {
		//TODO warning
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void shouldFindNoViolationsWhenTestFieldIsNotNullAndSizeIsBetweenMinAndMax() {
		trimmedSizeTestObject = new TrimmedSizeTestObject("test");

		Set<ConstraintViolation<TrimmedSizeTestObject>> violations = validator.validate(trimmedSizeTestObject);
		assertEquals(0, violations.size());
	}

	@Test
	void shouldFindNoViolationsWhenTestFieldIsNull() {
		trimmedSizeTestObject = new TrimmedSizeTestObject(null);

		Set<ConstraintViolation<TrimmedSizeTestObject>> violations = validator.validate(trimmedSizeTestObject);
		assertEquals(0, violations.size());
	}

	@Test
	void shouldFindViolationWhenMinSizeIsNotFulfilled() {
		trimmedSizeTestObject = new TrimmedSizeTestObject("  ");

		Set<ConstraintViolation<TrimmedSizeTestObject>> violations = validator.validate(trimmedSizeTestObject);
		assertEquals(1, violations.size());
	}

	@Test
	void shouldFindViolationWhenMinSizeIsFulfilledWithSpaces() {
		trimmedSizeTestObject = new TrimmedSizeTestObject("  ");

		Set<ConstraintViolation<TrimmedSizeTestObject>> violations = validator.validate(trimmedSizeTestObject);
		assertEquals(1, violations.size());
	}

	@Test
	void shouldFindViolationWhenMaxSizeIsExceeded() {
		trimmedSizeTestObject = new TrimmedSizeTestObject("123456789");

		Set<ConstraintViolation<TrimmedSizeTestObject>> violations = validator.validate(trimmedSizeTestObject);
		assertEquals(1, violations.size());
	}
}

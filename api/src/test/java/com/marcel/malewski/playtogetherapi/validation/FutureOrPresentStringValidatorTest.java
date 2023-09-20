package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.testObject.FutureOrPresentStringValidatorTestObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora?
class FutureOrPresentStringValidatorTest {
	private Validator validator;
	private FutureOrPresentStringValidatorTestObject futureOrPresentStringValidatorTestObject;

	@BeforeEach
	void setup() {
		//TODO warning
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void shouldFindNoViolationsWhenDateIsInFuture() {
		LocalDate today = LocalDate.now();
		LocalDate todayPlusOneDay= today.plusDays(1);
		futureOrPresentStringValidatorTestObject = new FutureOrPresentStringValidatorTestObject(todayPlusOneDay.toString());

		Set<ConstraintViolation<FutureOrPresentStringValidatorTestObject>> violations = validator.validate(futureOrPresentStringValidatorTestObject);
		assertEquals(0, violations.size());
	}

	@Test
	void shouldFindNoViolationsWhenDateIsInPresent() {
		LocalDate today = LocalDate.now();
		futureOrPresentStringValidatorTestObject = new FutureOrPresentStringValidatorTestObject(today.toString());

		Set<ConstraintViolation<FutureOrPresentStringValidatorTestObject>> violations = validator.validate(futureOrPresentStringValidatorTestObject);
		assertEquals(0, violations.size());
	}

	@Test
	void shouldFindViolationWhenDateIsInFuture() {
		LocalDate today = LocalDate.now();
		LocalDate todayMinusOneDay= today.minusDays(1);
		futureOrPresentStringValidatorTestObject = new FutureOrPresentStringValidatorTestObject(todayMinusOneDay.toString());

		Set<ConstraintViolation<FutureOrPresentStringValidatorTestObject>> violations = validator.validate(futureOrPresentStringValidatorTestObject);
		assertEquals(1, violations.size());
	}
}

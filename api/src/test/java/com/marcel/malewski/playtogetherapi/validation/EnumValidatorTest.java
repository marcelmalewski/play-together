package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.PrivacyLevel;
import com.marcel.malewski.playtogetherapi.testObject.EnumValidatorStringTestObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora?
class EnumValidatorTest {
	private Validator validator;
	private EnumValidatorStringTestObject enumValidatorStringTestObject;

	@BeforeEach
	void setup() {
		//TODO warning
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void shouldFindNoViolationsWhenEnumIsValid() {
		enumValidatorStringTestObject = new EnumValidatorStringTestObject(PrivacyLevel.PUBLIC.toString());

		Set<ConstraintViolation<EnumValidatorStringTestObject>> violations = validator.validate(enumValidatorStringTestObject);
		assertEquals(0, violations.size());
	}

	@Test
	void shouldFindViolationWhenEnumIsNotValid() {
		enumValidatorStringTestObject = new EnumValidatorStringTestObject("PrivacyLevel.PUBLIC.toString()");

		Set<ConstraintViolation<EnumValidatorStringTestObject>> violations = validator.validate(enumValidatorStringTestObject);
		assertEquals(1, violations.size());
	}
}

package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.testObject.StringDateFormatTestObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.marcel.malewski.playtogetherapi.util.TestGamerCreator.BIRTH_DATE;
import static com.marcel.malewski.playtogetherapi.util.TestGamerCreator.BIRTH_DATE_INVALID_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora?
class StringDateFormatValidatorTest {
  private Validator validator;
  private StringDateFormatTestObject stringDateFormatTestObject;

  @BeforeEach
  void setup() {
    //TODO warning
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenDateFormatIsValid() {
    stringDateFormatTestObject = new StringDateFormatTestObject(BIRTH_DATE);

    Set<ConstraintViolation<StringDateFormatTestObject>> violations = validator.validate(stringDateFormatTestObject);
    assertEquals(0, violations.size());
  }

  @Test
  void shouldFindViolationWhenDateFormatIsNotValid() {
    stringDateFormatTestObject = new StringDateFormatTestObject(BIRTH_DATE_INVALID_FORMAT);

    Set<ConstraintViolation<StringDateFormatTestObject>> violations = validator.validate(stringDateFormatTestObject);
    assertEquals(1, violations.size());
  }
}

package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.testObject.StringTimeFormatTestObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_INVALID_FORMAT;
import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_START;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora?
class StringTimeFormatValidatorTest {
  private Validator validator;
  private StringTimeFormatTestObject stringTimeFormatTestObject;

  @BeforeEach
  void setup() {
    //TODO warning
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenTimeFormatIsValid() {
    stringTimeFormatTestObject = new StringTimeFormatTestObject(PLAYING_TIME_START);

    Set<ConstraintViolation<StringTimeFormatTestObject>> violations = validator.validate(stringTimeFormatTestObject);
    assertEquals(0, violations.size());
  }

  @Test
  void shouldFindViolationWhenPlayingTimeStartIsNotValid() {
    stringTimeFormatTestObject = new StringTimeFormatTestObject(PLAYING_TIME_INVALID_FORMAT);

    Set<ConstraintViolation<StringTimeFormatTestObject>> violations = validator.validate(stringTimeFormatTestObject);
    assertEquals(1, violations.size());
  }
}

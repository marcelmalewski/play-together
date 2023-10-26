package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.testObject.TimeFormatStringTestObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_AS_STRING_INVALID_FORMAT;
import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_START_AS_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora?
class TimeFormatStringValidatorTest {
  private Validator validator;
  private TimeFormatStringTestObject timeFormatStringTestObject;

  @BeforeEach
  void setup() {
    //TODO warning
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenTimeFormatIsValid() {
    timeFormatStringTestObject = new TimeFormatStringTestObject(PLAYING_TIME_START_AS_STRING);

    Set<ConstraintViolation<TimeFormatStringTestObject>> violations = validator.validate(timeFormatStringTestObject);
    assertEquals(0, violations.size());
  }

  @Test
  void shouldFindViolationWhenPlayingTimeStartIsNotValid() {
    timeFormatStringTestObject = new TimeFormatStringTestObject(PLAYING_TIME_AS_STRING_INVALID_FORMAT);

    Set<ConstraintViolation<TimeFormatStringTestObject>> violations = validator.validate(timeFormatStringTestObject);
    assertEquals(1, violations.size());
  }
}

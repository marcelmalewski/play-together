package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.testObject.DateFormatStringTestObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.marcel.malewski.playtogetherapi.TestConstants.INVALID_DATE_AS_STRING_FORMAT;
import static com.marcel.malewski.playtogetherapi.TestConstants.VALID_DATE_AS_STRING_FORMAT;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora?
class DateFormatStringValidatorTest {
  private Validator validator;
  private DateFormatStringTestObject dateFormatStringTestObject;

  @BeforeEach
  void setup() {
    //TODO warning
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenDateFormatIsValid() {
    dateFormatStringTestObject = new DateFormatStringTestObject(VALID_DATE_AS_STRING_FORMAT);

    Set<ConstraintViolation<DateFormatStringTestObject>> violations = validator.validate(dateFormatStringTestObject);
    assertEquals(0, violations.size());
  }

  @Test
  void shouldFindViolationWhenDateFormatIsNotValid() {
    dateFormatStringTestObject = new DateFormatStringTestObject(INVALID_DATE_AS_STRING_FORMAT);

    Set<ConstraintViolation<DateFormatStringTestObject>> violations = validator.validate(dateFormatStringTestObject);
    assertEquals(1, violations.size());
  }
}

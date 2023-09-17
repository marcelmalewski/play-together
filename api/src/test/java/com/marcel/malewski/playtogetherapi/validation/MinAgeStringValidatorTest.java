package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.testObject.MinAgeStringTestObject;
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
class MinAgeStringValidatorTest {
  private Validator validator;
  private MinAgeStringTestObject minAgeStringTestObject;

  @BeforeEach
  void setup() {
    //TODO warning
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenAgeIsMinFifteenYears() {
    LocalDate today = LocalDate.now();
    LocalDate fifteenYearsBeforeToday = today.minusYears(15);
    minAgeStringTestObject = new MinAgeStringTestObject(fifteenYearsBeforeToday.toString());

    Set<ConstraintViolation<MinAgeStringTestObject>> violations = validator.validate(minAgeStringTestObject);
    assertEquals(0, violations.size());
  }

  @Test
  void shouldFindViolationWhenAgeIsLessThanFifteenYears() {
    LocalDate today = LocalDate.now();
    LocalDate fifteenYearsBeforeTodayPlusOneDay = today.minusYears(15).plusDays(1);
    minAgeStringTestObject = new MinAgeStringTestObject(fifteenYearsBeforeTodayPlusOneDay.toString());

    Set<ConstraintViolation<MinAgeStringTestObject>> violations = validator.validate(minAgeStringTestObject);
    assertEquals(1, violations.size());
  }
}

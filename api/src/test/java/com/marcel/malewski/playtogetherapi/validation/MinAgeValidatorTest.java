package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.testObject.MinAgeTestObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static com.marcel.malewski.playtogetherapi.util.TestGamerCreator.BIRTH_DATE;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora?
class MinAgeValidatorTest {
  private Validator validator;
  private MinAgeTestObject minAgeTestObject;

  @BeforeEach
  void setup() {
    //TODO warning
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenAgeIsMinFifteenYears() {
    minAgeTestObject = new MinAgeTestObject(BIRTH_DATE);

    Set<ConstraintViolation<MinAgeTestObject>> violations = validator.validate(minAgeTestObject);
    assertEquals(0, violations.size());
  }

  @Test
  void shouldFindViolationWhenAgeIsLessThanFifteenYears() {
    LocalDate today = LocalDate.now();
    LocalDate fifteenYearsBeforeTodayPlusOneDay = today.minusYears(15).plusDays(1);
    minAgeTestObject = new MinAgeTestObject(fifteenYearsBeforeTodayPlusOneDay.toString());

    Set<ConstraintViolation<MinAgeTestObject>> violations = validator.validate(minAgeTestObject);
    assertEquals(1, violations.size());
  }
}

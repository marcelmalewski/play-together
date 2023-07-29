package com.marcel.malewski.playtogetherapi.validation.minage;

import com.marcel.malewski.playtogetherapi.auth.register.GamerRegisterRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora i wtedy dodac test z nullami
class MinAgeValidatorTest {
  private ValidatorFactory factory;
  private Validator validator;
  private GamerRegisterRequestDto registerRequestDto;

  @BeforeEach
  void init() {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenAgeIsMinFifteenYears() {
    registerRequestDto = new GamerRegisterRequestDto(
      "username",
      "test1234534563456",
      "yes@yes.com",
      "2000-01-01",
      "15:00",
      "18:00",
      List.of(1L)
    );

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(0, violations.size());
  }

  @Test
  void shouldFindViolationWhenAgeIsLessThanFifteenYears() {
    LocalDate today = LocalDate.now();
    LocalDate fifteenYearsBeforeTodayPlusOneDay = today.minusYears(15).plusDays(1);
    registerRequestDto = new GamerRegisterRequestDto(
      "username",
      "test1234534563456",
      "yes@yes.com",
      fifteenYearsBeforeTodayPlusOneDay.toString(),
      "14:00",
      "15:00",
      List.of(1L)
    );

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(1, violations.size());
  }
}

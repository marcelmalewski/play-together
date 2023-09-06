package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.security.register.GamerRegisterRequestDto;
import com.marcel.malewski.playtogetherapi.util.TestGamerCreator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static com.marcel.malewski.playtogetherapi.util.TestGamerCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora i wtedy dodac test z nullami
class MinAgeValidatorTest {
  private Validator validator;
  private GamerRegisterRequestDto registerRequestDto;

  @BeforeEach
  void init() {
    //TODO warning
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenAgeIsMinFifteenYears() {
    registerRequestDto = TestGamerCreator.getValidGamerRegisterRequestDto();

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(0, violations.size());
  }

  @Test
  void shouldFindViolationWhenAgeIsLessThanFifteenYears() {
    LocalDate today = LocalDate.now();
    LocalDate fifteenYearsBeforeTodayPlusOneDay = today.minusYears(15).plusDays(1);
    registerRequestDto = new GamerRegisterRequestDto(
      LOGIN,
      PASSWORD,
      EMAIL,
      fifteenYearsBeforeTodayPlusOneDay.toString(),
      PLAYING_TIME_NINE_O_CLOCK,
      PLAYING_TIME_TEN_O_CLOCK,
      PLATFORMS_IDS
    );

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(1, violations.size());
  }
}

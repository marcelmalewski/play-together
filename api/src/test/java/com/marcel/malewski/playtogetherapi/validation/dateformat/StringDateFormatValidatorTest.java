package com.marcel.malewski.playtogetherapi.validation.dateformat;

import com.marcel.malewski.playtogetherapi.security.register.GamerRegisterRequestDto;
import com.marcel.malewski.playtogetherapi.validation.ValidGamerRegisterRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.marcel.malewski.playtogetherapi.validation.ValidationConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora i wtedy dodac test z nullami
class StringDateFormatValidatorTest {
  private ValidatorFactory factory;
  private Validator validator;
  private GamerRegisterRequestDto registerRequestDto;

  @BeforeEach
  void init() {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenDateFormatIsValid() {
    registerRequestDto = ValidGamerRegisterRequestDto.getValidGamerRegisterRequestDto();

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(0, violations.size());
  }

  @Test
  void shouldFindViolationWhenDateFormatIsNotValid() {
    registerRequestDto = new GamerRegisterRequestDto(
      LOGIN,
      PASSWORD,
      EMAIL,
      BIRTH_DATE_INVALID_FORMAT,
      PLAYING_TIME_NINE_O_CLOCK,
      PLAYING_TIME_TEN_O_CLOCK,
      PLATFORMS_IDS
    );

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(1, violations.size());
  }
}

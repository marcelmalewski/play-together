package com.marcel.malewski.playtogetherapi.validation.timeformat;

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
class StringTimeFormatValidatorTest {

  private ValidatorFactory factory;
  private Validator validator;
  private GamerRegisterRequestDto registerRequestDto;

  @BeforeEach
  void init() {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenTimeFormatIsValid() {
    registerRequestDto = ValidGamerRegisterRequestDto.getValidGamerRegisterRequestDto();

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(0, violations.size());
  }

  @Test
  void shouldFindViolationWhenPlayingTimeStartIsNotValid() {
    registerRequestDto = new GamerRegisterRequestDto(
      LOGIN,
      PASSWORD,
      EMAIL,
      BIRTH_DATE,
      PLAYING_TIME_INVALID_FORMAT,
	    PLAYING_TIME_TEN_O_CLOCK,
      PLATFORMS_IDS
    );

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(2, violations.size());
  }
}

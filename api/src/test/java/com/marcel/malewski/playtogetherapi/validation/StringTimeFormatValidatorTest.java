package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.security.register.GamerRegisterRequestDto;
import com.marcel.malewski.playtogetherapi.util.TestGamerCreator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.marcel.malewski.playtogetherapi.util.TestGamerCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora i wtedy dodac test z nullami
class StringTimeFormatValidatorTest {
  private Validator validator;
  private GamerRegisterRequestDto registerRequestDto;

  @BeforeEach
  void setup() {
    //TODO warning
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenTimeFormatIsValid() {
    registerRequestDto = TestGamerCreator.getValidGamerRegisterRequestDto();

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
	    PLAYING_TIME_END,
      PLATFORMS_IDS
    );

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(1, violations.size());
  }
}

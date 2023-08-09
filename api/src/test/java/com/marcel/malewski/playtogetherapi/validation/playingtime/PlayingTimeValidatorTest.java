package com.marcel.malewski.playtogetherapi.validation.playingtime;

import com.marcel.malewski.playtogetherapi.security.register.GamerRegisterRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora i wtedy dodac test z nullami
class PlayingTimeValidatorTest {
  private ValidatorFactory factory;
  private Validator validator;
  private GamerRegisterRequestDto registerRequestDto;

  @BeforeEach
  void init() {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  //TODO w paru testach validatorow jest jeden bardzo podobny test zweryfikowac czy to okej
  @Test
  void shouldFindNoViolationsWhenPlayingTimesAreValid() {
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
  void shouldFindViolationWhenEndTimeIsBeforeStartTime() {
    registerRequestDto = new GamerRegisterRequestDto(
      "username",
      "test1234534563456",
      "yes@yes.com",
      "2000-01-01",
      "15:00",
      "14:00",
      List.of(1L)
    );

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(1, violations.size());
  }
}

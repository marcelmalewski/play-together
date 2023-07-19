package com.marcel.malewski.playtogetherapi.validation.playingtime;

import com.marcel.malewski.playtogetherapi.auth.register.GamerRegisterRequestDto;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformEnum;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//TODO poprawic na test tylko specyficznego validatora
class PlayingTimeValidatorTest {
  private ValidatorFactory factory;
  private Validator validator;
  private GamerRegisterRequestDto registerRequestDto;

  @BeforeEach
  void init() {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenRequestIsValid() {
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

  //TODO finish
//  @Test
//  void shouldFindNoViolationsWhenPlayingTimesAreNulls() {
//    registerRequestDto = new GamerRegisterRequestDto(
//      "username",
//      "test1234534563456",
//      "yes@yes.com",
//      "2000-01-01",
//      null,
//      null,
//      List.of(1L)
//    );
//
//    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
//    assertEquals(0, violations.size());
//  }

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

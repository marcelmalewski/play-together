package com.marcel.malewski.playtogetherapi.validation.pastorpresent;

import com.marcel.malewski.playtogetherapi.auth.register.GamerRegisterRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//TODO poprawic na test tylko specyficznego validatora i wtedy dodac test z nullami
class StringPastOrPresentValidatorTest {
  private ValidatorFactory factory;
  private Validator validator;
  private GamerRegisterRequestDto registerRequestDto;

  @BeforeEach
  void init() {
    factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void shouldFindNoViolationsWhenBirthDateIsInPastOrPresent() {
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
  void shouldFindViolationWhenBirthDateIsInFuture() {
    LocalDate today = LocalDate.now();
    LocalDate tomorrow = today.plusDays(1);
    registerRequestDto = new GamerRegisterRequestDto(
      "username",
      "test1234534563456",
      "yes@yes.com",
      tomorrow.toString(),
      "14:00",
      "15:00",
      List.of(1L)
    );

    Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(registerRequestDto);
    assertEquals(1, violations.size());
  }
}

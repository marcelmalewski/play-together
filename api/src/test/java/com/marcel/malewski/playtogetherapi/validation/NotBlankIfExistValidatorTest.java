package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.util.TestGamerCreator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO dokończyć test
//TODO poprawic na test tylko specyficznego validatora i wtedy dodac test z nullami
public class NotBlankIfExistValidatorTest {
	private Validator validator;
	private GamerUpdateProfileRequestDto gamerUpdateProfileRequestDto;

	@BeforeEach
	void setup() {
		//TODO warning
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void shouldFindNoViolationsWhenNotBlankIfExist() {
		gamerUpdateProfileRequestDto = TestGamerCreator.getValidGamerUpdateProfileRequestDto();

		Set<ConstraintViolation<GamerUpdateProfileRequestDto>> violations = validator.validate(gamerUpdateProfileRequestDto);
		assertEquals(0, violations.size());
	}

//	void shouldFindNoViolationsWhenNotExist() {

	@Test
	void shouldFindViolationWhenAgeIsLessThanFifteenYears() {
//		LocalDate today = LocalDate.now();
//		LocalDate fifteenYearsBeforeTodayPlusOneDay = today.minusYears(15).plusDays(1);
//
//		Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(gamerUpdateProfileRequestDto);
//		assertEquals(1, violations.size());
	}
}

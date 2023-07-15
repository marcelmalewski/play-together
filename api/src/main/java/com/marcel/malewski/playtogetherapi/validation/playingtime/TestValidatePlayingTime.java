package com.marcel.malewski.playtogetherapi.validation.playingtime;

import com.marcel.malewski.playtogetherapi.auth.register.GamerRegisterRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO sprawdzić jak to działa
//TODO przenieść gdzie?
public class TestValidatePlayingTime {
	@Test
	public void testValidatePlayingTime() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		GamerRegisterRequestDto requestDto = new GamerRegisterRequestDto(
			"username",
			"test1234534563456",
			"yes@yes.com",
			"LocalDate.now()",
			"LocalTime.of(19, 0)",
			"LocalTime.of(10, 0)",
			null
//			List.of(PlatformEnum.PC)
		);

		Set<ConstraintViolation<GamerRegisterRequestDto>> violations = validator.validate(requestDto);
		assertEquals(1, violations.size());
	}
}

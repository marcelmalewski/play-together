package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.testObject.PlayingTimeTestObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_END;
import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_START;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora?
class PlayingTimeValidatorTest {
	private Validator validator;
	private PlayingTimeTestObject playingTimeTestObject;

	@BeforeEach
	void setup() {
		//TODO warning
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void shouldFindNoViolationsWhenPlayingTimesAreValid() {
		playingTimeTestObject = new PlayingTimeTestObject(PLAYING_TIME_START, PLAYING_TIME_END);

		Set<ConstraintViolation<PlayingTimeTestObject>> violations = validator.validate(playingTimeTestObject);
		assertEquals(0, violations.size());
	}

	@Test
	void shouldFindViolationWhenEndTimeIsBeforeStartTime() {
    playingTimeTestObject = new PlayingTimeTestObject(PLAYING_TIME_END, PLAYING_TIME_START);

    Set<ConstraintViolation<PlayingTimeTestObject>> violations = validator.validate(playingTimeTestObject);
		assertEquals(1, violations.size());
	}
}

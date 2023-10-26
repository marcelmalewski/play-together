package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.testObject.PlayingTimeStringTestObject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_END_AS_STRING;
import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_START_AS_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO poprawic na test tylko specyficznego validatora?
class PlayingTimeStringValidatorTest {
	private Validator validator;
	private PlayingTimeStringTestObject playingTimeStringTestObject;

	@BeforeEach
	void setup() {
		//TODO warning
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void shouldFindNoViolationsWhenPlayingTimesAreValid() {
		playingTimeStringTestObject = new PlayingTimeStringTestObject(PLAYING_TIME_START_AS_STRING, PLAYING_TIME_END_AS_STRING);

		Set<ConstraintViolation<PlayingTimeStringTestObject>> violations = validator.validate(playingTimeStringTestObject);
		assertEquals(0, violations.size());
	}

	@Test
	void shouldFindViolationWhenEndTimeIsBeforeStartTime() {
    playingTimeStringTestObject = new PlayingTimeStringTestObject(PLAYING_TIME_END_AS_STRING, PLAYING_TIME_START_AS_STRING);

    Set<ConstraintViolation<PlayingTimeStringTestObject>> violations = validator.validate(playingTimeStringTestObject);
		assertEquals(1, violations.size());
	}
}

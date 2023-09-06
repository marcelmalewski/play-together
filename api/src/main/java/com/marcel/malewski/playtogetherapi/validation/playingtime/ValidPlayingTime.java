package com.marcel.malewski.playtogetherapi.validation.playingtime;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlayingTimeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPlayingTime {
	String message() default "start time must be earlier than end time";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

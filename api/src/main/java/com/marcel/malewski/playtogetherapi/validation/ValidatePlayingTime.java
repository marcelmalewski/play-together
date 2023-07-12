package com.marcel.malewski.playtogetherapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlayingTimeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatePlayingTime {
	String message() default "start time must be earlier than end time";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

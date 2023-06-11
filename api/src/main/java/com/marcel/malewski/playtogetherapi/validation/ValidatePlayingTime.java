package com.marcel.malewski.playtogetherapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

//TODO poprawić wiadomość
@Documented
@Constraint(validatedBy = PlayingTimeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatePlayingTime {
	String message() default "Start time should be before end time.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

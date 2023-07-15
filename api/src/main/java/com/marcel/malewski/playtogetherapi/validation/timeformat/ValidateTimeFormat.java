package com.marcel.malewski.playtogetherapi.validation.timeformat;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeFormatValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateTimeFormat {
	String message() default "expected time format is HH:mm";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}


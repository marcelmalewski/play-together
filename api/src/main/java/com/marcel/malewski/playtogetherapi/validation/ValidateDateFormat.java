package com.marcel.malewski.playtogetherapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateDateFormat {
	String message() default "expected date format is yyyy-MM-dd";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

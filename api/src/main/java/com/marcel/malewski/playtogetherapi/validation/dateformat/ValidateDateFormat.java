package com.marcel.malewski.playtogetherapi.validation.dateformat;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringDateFormatValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateDateFormat {
	String message() default "expected date format is uuuu-MM-dd";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

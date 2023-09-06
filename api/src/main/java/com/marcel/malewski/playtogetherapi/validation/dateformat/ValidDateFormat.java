package com.marcel.malewski.playtogetherapi.validation.dateformat;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.marcel.malewski.playtogetherapi.constants.DateConstants.DATE_FORMAT;

@Documented
@Constraint(validatedBy = StringDateFormatValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateFormat {
	String message() default "expected date format is " + DATE_FORMAT;
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

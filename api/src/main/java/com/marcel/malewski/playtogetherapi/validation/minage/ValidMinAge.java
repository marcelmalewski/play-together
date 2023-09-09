package com.marcel.malewski.playtogetherapi.validation.minage;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.MIN_AGE;

@Documented
@Constraint(validatedBy = MinAgeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMinAge {
	String message() default "minimum age is " + MIN_AGE;
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

package com.marcel.malewski.playtogetherapi.validation.minage;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinAgeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMinAge {
	//TODO czy ten message jest ok?
	String message() default "minimum age is 15";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

package com.marcel.malewski.playtogetherapi.validation.enums;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
	Class<? extends Enum<?>> enumClass();

	String message() default "must be one of values: {enumValues}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

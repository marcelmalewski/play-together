package com.marcel.malewski.playtogetherapi.validation.notblankifexist;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

//TODO dodać test
@Documented
@Constraint(validatedBy = NotBlankIfExistValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfExist {
	String message() default "must not be empty if exist";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

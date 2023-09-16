package com.marcel.malewski.playtogetherapi.validation.notblankifexist;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

//TODO może poprawić na trimmed tylko z boków, a w środku spacje są git
@Documented
@Constraint(validatedBy = TrimmedSizeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrimmedSize {
	String message() default "size without spaces must be between {min} and {max}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	int min() default 0;
	int max() default Integer.MAX_VALUE;
}

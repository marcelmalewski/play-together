package com.marcel.malewski.playtogetherapi.validation.futureorpresent;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FutureOrPresentStringValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureOrPresentCustom {
	String message() default "{jakarta.validation.constraints.FutureOrPresent.message}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

package com.marcel.malewski.playtogetherapi.validation.pastorpresent;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringPastOrPresentValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateStringPastOrPresent {
	String message() default "expected date format is yyyy-MM-dd";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

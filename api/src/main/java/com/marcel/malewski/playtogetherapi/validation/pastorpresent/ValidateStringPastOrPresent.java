package com.marcel.malewski.playtogetherapi.validation.pastorpresent;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringPastOrPresentValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateStringPastOrPresent {
	//TODO czy ten message jest ok?
	String message() default "birthdate can not be in future";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

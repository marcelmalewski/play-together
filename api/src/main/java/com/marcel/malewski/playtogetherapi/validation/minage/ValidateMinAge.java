package com.marcel.malewski.playtogetherapi.validation.minage;

import com.marcel.malewski.playtogetherapi.validation.pastorpresent.StringPastOrPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringPastOrPresentValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateMinAge {
	//TODO czy ten message jest ok?
	String message() default "minimum age is 15";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

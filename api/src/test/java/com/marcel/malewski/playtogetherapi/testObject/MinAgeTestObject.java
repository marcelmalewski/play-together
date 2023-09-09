package com.marcel.malewski.playtogetherapi.testObject;

import com.marcel.malewski.playtogetherapi.validation.minage.ValidMinAge;

public record MinAgeTestObject(
	@ValidMinAge
	String birthDate
) {
}

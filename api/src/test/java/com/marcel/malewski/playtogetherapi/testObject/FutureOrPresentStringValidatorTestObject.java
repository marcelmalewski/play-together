package com.marcel.malewski.playtogetherapi.testObject;

import com.marcel.malewski.playtogetherapi.validation.futureorpresent.FutureOrPresentCustom;

public record FutureOrPresentStringValidatorTestObject(
	@FutureOrPresentCustom
	String testField
) {
}

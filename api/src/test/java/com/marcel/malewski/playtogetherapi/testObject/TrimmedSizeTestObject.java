package com.marcel.malewski.playtogetherapi.testObject;

import com.marcel.malewski.playtogetherapi.validation.notblankifexist.TrimmedSize;

public record TrimmedSizeTestObject(
	@TrimmedSize(min = 1, max = 8)
	String testField
) {
}

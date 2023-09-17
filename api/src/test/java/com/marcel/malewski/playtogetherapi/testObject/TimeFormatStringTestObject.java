package com.marcel.malewski.playtogetherapi.testObject;

import com.marcel.malewski.playtogetherapi.validation.timeformat.ValidTimeFormat;

public record TimeFormatStringTestObject(
	@ValidTimeFormat
	String timeAsString
) {
}

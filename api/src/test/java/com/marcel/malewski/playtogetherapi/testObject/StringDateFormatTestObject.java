package com.marcel.malewski.playtogetherapi.testObject;

import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidDateFormat;

public record StringDateFormatTestObject(
	@ValidDateFormat
	String dateAsString
) {
}

package com.marcel.malewski.playtogetherapi.testObject;

import com.marcel.malewski.playtogetherapi.validation.dateformat.ValidDateFormat;

public record DateFormatStringTestObject(
	@ValidDateFormat
	String dateAsString
) {
}

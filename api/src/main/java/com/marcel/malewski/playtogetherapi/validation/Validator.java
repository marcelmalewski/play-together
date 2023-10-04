package com.marcel.malewski.playtogetherapi.validation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class Validator {
	private Validator() {
	}

	public static boolean ValidateDateTimeFormat(String dateTimeAsString, String formatPattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatPattern).withResolverStyle(ResolverStyle.STRICT);
		if (dateTimeAsString == null) {
			return true;
		}

		try {
			dateTimeFormatter.parse(dateTimeAsString);
		} catch (DateTimeParseException exception) {
			return false;
		}

		return true;
	}
}

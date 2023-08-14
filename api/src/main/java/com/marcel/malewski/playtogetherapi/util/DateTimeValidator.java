package com.marcel.malewski.playtogetherapi.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class DateTimeValidator {
	private DateTimeValidator() {
	}

	public static boolean ValidateDateTimeFormat(String dateTime, String formatPattern) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(formatPattern).withResolverStyle(ResolverStyle.STRICT);
		if (dateTime == null) {
			return true;
		}

		try {
			timeFormatter.parse(dateTime);
		} catch (DateTimeParseException exception) {
			return false;
		}

		return true;
	}
}

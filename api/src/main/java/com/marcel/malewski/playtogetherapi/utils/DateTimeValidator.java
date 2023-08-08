package com.marcel.malewski.playtogetherapi.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class DateTimeValidator {
	private DateTimeValidator() {
	}

	public static boolean ValidateDateTimeFormat(String time, String timeFormat) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormat).withResolverStyle(ResolverStyle.STRICT);
		if (time == null) {
			return true;
		}

		try {
			timeFormatter.parse(time);
		} catch (DateTimeParseException exception) {
			return false;
		}

		return true;
	}
}

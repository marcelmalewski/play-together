package com.marcel.malewski.playtogetherapi.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class DateUtils {
	public static final String DATE_FORMAT = "uuuu-MM-dd";
	public static final String TIME_FORMAT = "HH:mm";
	public static final int MIN_AGE = 15;

	public static boolean ValidateDateTimeFormat(String time, String timeFormat) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeFormat).withResolverStyle(ResolverStyle.STRICT);
		if(time == null) {
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

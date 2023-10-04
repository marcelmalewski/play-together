package com.marcel.malewski.playtogetherapi.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Optional;

public final class DateTimeParser {
	private DateTimeParser() {}

	public static Optional<LocalDate> tryParseToDate(String dateAsString, String formatPattern) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatPattern).withResolverStyle(ResolverStyle.STRICT);
		LocalDate date;
		try {
			date = LocalDate.parse(dateAsString, dateTimeFormatter);
		} catch (DateTimeParseException | NullPointerException exception) {
			return Optional.empty();
		}

		return Optional.of(date);
	}
}

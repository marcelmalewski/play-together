package com.marcel.malewski.playtogetherapi.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Optional;

import static com.marcel.malewski.playtogetherapi.constant.DateConstants.DATE_FORMAT;

public final class DateTimeParser {
	private DateTimeParser() {}

	public static Optional<LocalDate> tryParseToDate(String dateAsString) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
		LocalDate date;
		try {
			date = LocalDate.parse(dateAsString, dateFormatter);
		} catch (DateTimeParseException | NullPointerException exception) {
			return Optional.empty();
		}

		return Optional.of(date);
	}
}

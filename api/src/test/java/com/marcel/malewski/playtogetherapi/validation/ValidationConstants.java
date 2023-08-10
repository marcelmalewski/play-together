package com.marcel.malewski.playtogetherapi.validation;

import java.util.List;

public final class ValidationConstants {
	public static String LOGIN = "validLogin";
	public static String PASSWORD = "test123451345134";
	public static String EMAIL = "test@test.test";
	public static String BIRTH_DATE = "2000-01-01";
	public static String BIRTH_DATE_INVALID_FORMAT = "2000-01.01";

	public static String PLAYING_TIME_NINE_O_CLOCK = "09:00";
	public static String PLAYING_TIME_TEN_O_CLOCK = "10:00";
	public static String PLAYING_TIME_INVALID_FORMAT = "1800";

	public static List<Long> PLATFORMS_IDS = List.of(1L);

	private ValidationConstants() {
	}
}

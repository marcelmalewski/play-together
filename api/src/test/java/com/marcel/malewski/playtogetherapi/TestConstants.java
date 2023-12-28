package com.marcel.malewski.playtogetherapi;

public final class TestConstants {
	public static final String VALID_DATE_AS_STRING_FORMAT = "2000-01-01";
	public static final String INVALID_DATE_AS_STRING_FORMAT = "2000-01.01";

	public static final String PLAYING_TIME_START_AS_STRING = "09:00";
	public static final String PLAYING_TIME_END_AS_STRING = "10:00";
	public static final String PLAYING_TIME_AS_STRING_INVALID_FORMAT = "1800";
	public static final String POSTGRES_IMAGE = "postgres:12";

	public static final int NUMBER_OF_GAMERS_IN_TEST_DATABASE = 12;
	public static final long ID_OF_GAMER_THAT_NOT_EXIST = 999L;


	private TestConstants() {}
}

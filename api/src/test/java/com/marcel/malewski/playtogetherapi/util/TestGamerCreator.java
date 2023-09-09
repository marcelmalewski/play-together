package com.marcel.malewski.playtogetherapi.util;

import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.security.register.GamerRegisterRequestDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_END;
import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_START;

public final class TestGamerCreator {
	public static final String LOGIN = "validLogin";
	public static final String PASSWORD = "test123451345134";
	public static final String EMAIL = "test@test.test";
	public static final String BIRTH_DATE = "2000-01-01";
	public static final String BIO = "test bio";
	public static final String AVATAR_URL = "avatar url";

	public static List<Long> PLATFORMS_IDS = List.of(1L);
	public static List<Long> FAVOURITE_GAMES_IDS = List.of(1L);
	public static List<Long> FAVOURITE_GENRES_IDS = List.of(1L);

	private TestGamerCreator() {
	}

	//TODO te daty niżej to powinny być jakieś zmienne?
	public static Gamer getTestGamer(List<Platform> platforms, List<GamerRole> roles) {
		return Gamer.builder()
			.id(1L)
			.login(LOGIN)
			.password(PASSWORD)
			.email(EMAIL)
			.birthdate(LocalDate.of(2000, 1, 1))
			.playingTimeStart(LocalTime.of(15, 0))
			.playingTimeEnd(LocalTime.of(18, 0))
			.createdAt(LocalDate.now())
			.roles(roles)
			.platforms(platforms)
			.build();
	}

	public static GamerRegisterRequestDto getValidGamerRegisterRequestDto() {
		return new GamerRegisterRequestDto(
			LOGIN,
			PASSWORD,
			EMAIL,
			BIRTH_DATE,
			PLAYING_TIME_START,
			PLAYING_TIME_END,
			PLATFORMS_IDS
		);
	}

	public static GamerUpdateProfileRequestDto getValidGamerUpdateProfileRequestDto() {
		return new GamerUpdateProfileRequestDto(
			LOGIN,
			BIRTH_DATE,
			BIO,
			AVATAR_URL,
			PLAYING_TIME_START,
			PLAYING_TIME_END,
			PLATFORMS_IDS,
			FAVOURITE_GAMES_IDS,
			FAVOURITE_GENRES_IDS
		);
	}
}

package com.marcel.malewski.playtogetherapi.util;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthenticationDataRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.genre.Genre;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_END_AS_STRING;
import static com.marcel.malewski.playtogetherapi.TestConstants.PLAYING_TIME_START_AS_STRING;

public final class TestGamerCreator {
	public static final String LOGIN = "validLogin";
	public static final String INVALID_LOGIN = "";
	public static final String PASSWORD = "test123451345134";

	public static final String EMAIL = "test@test.test";
	public static final String INVALID_EMAIL = "invalid email";

	public static final String BIRTH_DATE_AS_STRING = "2000-01-01";
	public static final int BIRTH_DATE_YEAR = 2000;
	public static final int BIRTH_DATE_MONTH = 1;
	public static final int BIRTH_DATE_DAY = 1;

	public static final int PLAYING_TIME_START_HOUR = 15;
	public static final int PLAYING_TIME_START_MINUTES = 0;
	public static final int PLAYING_TIME_END_HOUR = 19;
	public static final int PLAYING_TIME_END_MINUTES = 0;

	public static final String BIO = "test bio";
	public static final String AVATAR_URL = "test avatar url";


	public static List<Long> PLATFORMS_IDS = List.of(1L);
	public static List<Long> FAVOURITE_GAMES_IDS = List.of(1L);
	public static List<Integer> FAVOURITE_GENRES_IDS = List.of(1);

	private TestGamerCreator() {
	}

	public static Gamer getTestGamer(List<Platform> platforms, List<GamerRole> roles) {
		return Gamer.builder()
			.id(1L)
			.version(1)
			.login(LOGIN)
			.password(PASSWORD)
			.email(EMAIL)
			.birthdate(LocalDate.of(BIRTH_DATE_YEAR, BIRTH_DATE_MONTH, BIRTH_DATE_DAY))
			.playingTimeStart(LocalTime.of(PLAYING_TIME_START_HOUR, PLAYING_TIME_START_MINUTES))
			.playingTimeEnd(LocalTime.of(PLAYING_TIME_END_HOUR, PLAYING_TIME_END_MINUTES))
			.createdAt(LocalDate.now())
			.roles(roles)
			.platforms(platforms)
			.build();
	}

	public static Gamer getTestGamerToSave(List<Platform> platforms, List<GamerRole> roles) {
		return Gamer.builder()
			.login(LOGIN)
			.password(PASSWORD)
			.email(EMAIL)
			.birthdate(LocalDate.of(BIRTH_DATE_YEAR, BIRTH_DATE_MONTH, BIRTH_DATE_DAY))
			.playingTimeStart(LocalTime.of(PLAYING_TIME_START_HOUR, PLAYING_TIME_START_MINUTES))
			.playingTimeEnd(LocalTime.of(PLAYING_TIME_END_HOUR, PLAYING_TIME_END_MINUTES))
			.createdAt(LocalDate.now())
			.roles(roles)
			.platforms(platforms)
			.build();
	}

	public static Gamer getGamerShallowCopy(Gamer gamer) {
		return Gamer.builder()
			.id(gamer.getId())
			.version(gamer.getVersion())
			.login(gamer.getLogin())
			.password(gamer.getPassword())
			.email(gamer.getEmail())
			.birthdate(gamer.getBirthdate())
			.playingTimeStart(gamer.getPlayingTimeStart())
			.playingTimeEnd(gamer.getPlayingTimeEnd())
			.createdAt(LocalDate.now())
			.roles(gamer.getRoles())
			.platforms(gamer.getPlatforms())
			.build();
	}

	public static GamerPublicResponseDto toGamerPublicResponseDto(Gamer gamer) {
		//TODO duplikat w GamerMapper?
		LocalDate currentDay = LocalDate.now();
		int age = Period.between(gamer.getBirthdate(), currentDay).getYears();
		List<String> platformsNames = gamer.getPlatforms().stream().map(Platform::getName).toList();
		List<String> gamesNames = gamer.getFavouriteGames().stream().map(Game::getName).toList();
		List<String> genresNames = gamer.getFavouriteGenres().stream().map(Genre::getName).toList();

		return new GamerPublicResponseDto(
			gamer.getId(),
			gamer.getLogin(),
			age,
			gamer.getBio(),
			gamer.getAvatarUrl(),
			gamer.getPlayingTimeStart(),
			gamer.getPlayingTimeEnd(),
			platformsNames,
			gamesNames,
			genresNames
		);
	}

	public static GamerPrivateResponseDto toGamerPrivateResponseDto(Gamer gamer) {
		List<String> platformsNames = gamer.getPlatforms().stream().map(Platform::getName).toList();
		List<String> gamesNames = gamer.getFavouriteGames().stream().map(Game::getName).toList();
		List<String> genresNames = gamer.getFavouriteGenres().stream().map(Genre::getName).toList();

		return new GamerPrivateResponseDto(
			gamer.getId(),
			gamer.getLogin(),
			gamer.getEmail(),
			gamer.getBirthdate(),
			gamer.getBio(),
			gamer.getAvatarUrl(),
			gamer.getPlayingTimeStart(),
			gamer.getPlayingTimeEnd(),
			platformsNames,
			gamesNames,
			genresNames
		);
	}

	public static GamerUpdateProfileRequestDto toGamerUpdateProfileRequestDto(Gamer gamer) {
		List<Long> platformsIds = gamer.getPlatforms().stream().map(Platform::getId).toList();
		List<Long> gamesIds = gamer.getFavouriteGames().stream().map(Game::getId).toList();
		List<Integer> genresIds = gamer.getFavouriteGenres().stream().map(Genre::getId).toList();

		return new GamerUpdateProfileRequestDto(
			gamer.getLogin(),
			gamer.getBirthdate().toString(),
			gamer.getBio(),
			gamer.getAvatarUrl(),
			gamer.getPlayingTimeStart().toString(),
			gamer.getPlayingTimeEnd().toString(),
			platformsIds,
			gamesIds,
			genresIds
		);
	}

	public static GamerUpdateProfileRequestDto getInValidGamerUpdateProfileRequestDto() {
		return new GamerUpdateProfileRequestDto(
			INVALID_LOGIN,
			BIRTH_DATE_AS_STRING,
			BIO,
			AVATAR_URL,
			PLAYING_TIME_START_AS_STRING,
			PLAYING_TIME_END_AS_STRING,
			PLATFORMS_IDS,
			FAVOURITE_GAMES_IDS,
			FAVOURITE_GENRES_IDS
		);
	}

	public static GamerUpdateAuthenticationDataRequestDto toGamerUpdateAuthenticationDataRequestDto(Gamer gamer) {
		return new GamerUpdateAuthenticationDataRequestDto(
			gamer.getEmail(),
			gamer.getPassword(),
			gamer.getPassword()
		);
	}

	public static GamerUpdateAuthenticationDataRequestDto getInvalidGamerUpdateAuthenticationDataRequestDto() {
		return new GamerUpdateAuthenticationDataRequestDto(
			INVALID_EMAIL,
			PASSWORD,
			PASSWORD
		);
	}
}

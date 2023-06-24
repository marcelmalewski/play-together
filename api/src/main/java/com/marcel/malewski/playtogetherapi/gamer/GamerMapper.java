package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.game.Game;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.genre.Genre;
import com.marcel.malewski.playtogetherapi.platform.Platform;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Mapper(componentModel = "spring")
abstract class GamerMapper {
	public GamerPrivateResponseDto toGamerPrivateResponseDto(Gamer gamer) {
		//TODO takie zmienianie to może lepiej bezpośrednio przy pobieraniu z bazy?
		List<String> platforms = gamer.getPlatforms().stream().map(Platform::getName).toList();
		List<String> games = gamer.getFavouriteGames().stream().map(Game::getName).toList();
		List<String> genres = gamer.getFavouriteGenres().stream().map(Genre::getName).toList();

		return new GamerPrivateResponseDto(
			gamer.getLogin(),
			gamer.getEmail(),
			gamer.getBirthDate(),
			gamer.getBio(),
			gamer.getAvatarUrl(),
			gamer.getPlayingTimeStart(),
			gamer.getPlayingTimeEnd(),
			platforms,
			games,
			genres
		);
	}

	public GamerPublicResponseDto toGamerPublicResponseDto(Gamer gamer) {
		//TODO takie zmienianie to może lepiej bezpośrednio przy pobieraniu z bazy?
		LocalDate currentDay = LocalDate.now();
		int age = Period.between(gamer.getBirthDate(), currentDay).getYears();
		List<String> platforms = gamer.getPlatforms().stream().map(Platform::getName).toList();
		List<String> games = gamer.getFavouriteGames().stream().map(Game::getName).toList();
		List<String> genres = gamer.getFavouriteGenres().stream().map(Genre::getName).toList();

		return new GamerPublicResponseDto(
			gamer.getLogin(),
			age,
			gamer.getBio(),
			gamer.getAvatarUrl(),
			gamer.getPlayingTimeStart(),
			gamer.getPlayingTimeEnd(),
			platforms,
			games,
			genres
		);
	}
}

package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.genre.Genre;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

//TODO tu jest powtórzenie
@Mapper(componentModel = "spring")
public abstract class GamerMapper {
	public GamerPrivateResponseDto toGamerPrivateResponseDto(@NotNull Gamer gamer) {
		//TODO takie zmienianie to może lepiej bezpośrednio przy pobieraniu z bazy?
		List<String> platforms = gamer.getPlatforms().stream().map(Platform::getName).toList();
		List<String> games = gamer.getFavouriteGames().stream().map(Game::getName).toList();
		List<String> genres = gamer.getFavouriteGenres().stream().map(Genre::getName).toList();

		return new GamerPrivateResponseDto(
			gamer.getId(),
			gamer.getLogin(),
			gamer.getEmail(),
			gamer.getBirthdate(),
			gamer.getBio(),
			gamer.getAvatarUrl(),
			gamer.getPlayingTimeStart(),
			gamer.getPlayingTimeEnd(),
			platforms,
			games,
			genres
		);
	}

	public GamerPublicResponseDto toGamerPublicResponseDto(@NotNull Gamer gamer) {
		//TODO takie zmienianie to może lepiej bezpośrednio przy pobieraniu z bazy?
		LocalDate currentDay = LocalDate.now();
		int age = Period.between(gamer.getBirthdate(), currentDay).getYears();
		List<String> platforms = gamer.getPlatforms().stream().map(Platform::getName).toList();
		List<String> games = gamer.getFavouriteGames().stream().map(Game::getName).toList();
		List<String> genres = gamer.getFavouriteGenres().stream().map(Genre::getName).toList();

		return new GamerPublicResponseDto(
			gamer.getId(),
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

package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.genre.Genre;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

//TODO is this duplicate a problem?
@Mapper(componentModel = "spring")
public abstract class GamerMapper {
	public GamerPrivateResponseDto toGamerPrivateResponseDto(Gamer gamer) {
		if(gamer == null) {
			return null;
		}

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

	public GamerPublicResponseDto toGamerPublicResponseDto(Gamer gamer) {
		if(gamer == null) {
			return null;
		}

		List<String> platformsNames = gamer.getPlatforms().stream().map(Platform::getName).toList();
		List<String> gamesNames = gamer.getFavouriteGames().stream().map(Game::getName).toList();
		List<String> genresNames = gamer.getFavouriteGenres().stream().map(Genre::getName).toList();
		int age = getAge(gamer);

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

	private int getAge(Gamer gamer) {
		LocalDate currentDay = LocalDate.now();
		return Period.between(gamer.getBirthdate(), currentDay).getYears();
	}
}

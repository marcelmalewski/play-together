package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.game.Game;
import com.marcel.malewski.playtogetherapi.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.genre.Genre;
import com.marcel.malewski.playtogetherapi.platform.Platform;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
abstract class GamerMapper {
	public GamerPrivateResponseDto toGamerPrivateResponseDto(Gamer gamer) {
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
}

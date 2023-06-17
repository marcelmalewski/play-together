package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.platform.Platform;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
abstract class GamerMapper {
	public GamerPrivateResponseDto toGamerPrivateResponseDto(Gamer gamer) {
		List<String> platforms = gamer.getPlatforms().stream().map(Platform::getName).toList();
		return new GamerPrivateResponseDto(
			gamer.getLogin(),
			gamer.getEmail(),
			gamer.getBirthDate(),
			gamer.getBio(),
			gamer.getAvatarUrl(),
			gamer.getPlayingTimeStart(),
			gamer.getPlayingTimeEnd(),
			platforms,
			gamer.getFavouriteGames(),
			gamer.getFavouriteGenres()
		);
	}
}

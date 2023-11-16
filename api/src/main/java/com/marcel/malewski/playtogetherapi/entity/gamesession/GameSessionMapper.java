package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GameSessionMapper {
	public GameSessionPublicResponseDto toGameSessionResponseDto(GameSession gameSession, long principalId) {
		//TODO if null return null
		List<String> platformsNames = gameSession.getPlatforms().stream().map(Platform::getName).toList();
		boolean currentGamerIsMember = !gameSession.getMembers().stream().filter(gamer -> gamer.getId().equals(principalId)).toList().isEmpty();

		return new GameSessionPublicResponseDto(
			gameSession.getId(),
			gameSession.getName(),
			gameSession.isCompetitive(),
			gameSession.getAccessType(),
			gameSession.getDate(),
			gameSession.getCreatedAt(),
			gameSession.getModifiedAt(),
			gameSession.getNumberOfMembers(),
			gameSession.getMaxMembers(),
			gameSession.getMinAge(),
			gameSession.getDescription(),
			gameSession.getCreator().getLogin(),
			gameSession.getGame().getName(),
			platformsNames,
			currentGamerIsMember
		);
	}
}

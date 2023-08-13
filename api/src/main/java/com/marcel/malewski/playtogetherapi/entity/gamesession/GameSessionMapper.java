package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionResponseDto;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class GameSessionMapper {
	public GameSessionResponseDto toGameSessionResponseDto(@NotNull GameSession gameSession) {
		List<String> platforms = gameSession.getPlatforms().stream().map(Platform::getName).toList();

		return new GameSessionResponseDto(
			gameSession.getId(),
			gameSession.getName(),
			gameSession.isCompetitive(),
			gameSession.getAccessType(),
			gameSession.getDate(),
			gameSession.getNumberOfMembers(),
			gameSession.getMaxMembers(),
			gameSession.getMinAge(),
			gameSession.isCurrentGamerMember(),
			gameSession.getDescription(),
			gameSession.getPendingMembers(),
			gameSession.getCreator().getLogin(),
			platforms
		);
	}
}

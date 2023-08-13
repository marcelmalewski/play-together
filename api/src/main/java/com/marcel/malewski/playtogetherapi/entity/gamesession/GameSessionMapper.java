package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionResponseDto;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;

import java.util.List;

public class GameSessionMapper {
	GameSessionResponseDto toGameSessionResponseDto(GameSession gameSession) {
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
			gameSession.getAvailabilityTimes(),
			gameSession.getCreator().getLogin(),
			gameSession.getGame().getName(),
			platforms
		);
	}
}

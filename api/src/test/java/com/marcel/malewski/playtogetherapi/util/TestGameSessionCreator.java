package com.marcel.malewski.playtogetherapi.util;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSession;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.enums.PrivacyLevel;

import java.time.LocalDate;
import java.util.List;

public final class TestGameSessionCreator {
	private TestGameSessionCreator() {
	}

	public static GameSession getTestGameSession(Gamer creator, Game game, List<Platform> platforms) {
		LocalDate today = LocalDate.now();

		return GameSession.builder()
			.id(1L)
			.name("test game session")
			.visibilityType(PrivacyLevel.PUBLIC)
			.isCompetitive(false)
			.accessType(PrivacyLevel.PUBLIC)
			.date(today)
			.createdAt(today)
			.modifiedAt(today)
			.numberOfMembers(1)
			.maxMembers(20)
			.minAge(15)
			.creator(creator)
			.game(game)
			.members(List.of(creator))
			.platforms(platforms)
			.build();
	}

	public static GameSessionPublicResponseDto toGameSessionResponseDto(GameSession gameSession, List<String> platforms, boolean currentGamerIsMember) {
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
			platforms,
			currentGamerIsMember
		);
	}
}

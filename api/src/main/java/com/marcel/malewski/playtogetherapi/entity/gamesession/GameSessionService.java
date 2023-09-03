package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameService;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionCreateOrUpdateRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.exception.GameSessionNotFoundException;
import com.marcel.malewski.playtogetherapi.entity.gamesession.exception.TryToUpdateGameSessionWithoutRoleGameSessionOwnerException;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformService;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@Validated
public class GameSessionService {
	private final GameSessionRepository gameSessionRepository;
	private final GameSessionMapper gameSessionMapper;
	private final PlatformService platformService;
	private final GamerService gamerService;
	private final GameService gameService;

	public GameSessionService(GameSessionRepository gameSessionRepository, GameSessionMapper gameSessionMapper, PlatformService platformService, GamerService gamerService, GameService gameService) {
		this.gameSessionRepository = gameSessionRepository;
		this.gameSessionMapper = gameSessionMapper;
		this.platformService = platformService;
		this.gamerService = gamerService;
		this.gameService = gameService;
	}

	public Page<GameSessionPublicResponseDto> findAllGameSessions(@NotNull Pageable pageable, long principalId) {
		return gameSessionRepository.findAll(pageable).map(gameSession -> gameSessionMapper.toGameSessionResponseDto(gameSession, principalId));
	}

	public GameSessionPublicResponseDto getGameSession(long gameSessionId, long principalId) {
		GameSession gameSession = gameSessionRepository.findById(gameSessionId).orElseThrow(() -> new GameSessionNotFoundException(gameSessionId));
		return gameSessionMapper.toGameSessionResponseDto(gameSession, principalId);
	}

	public GameSessionPublicResponseDto saveGameSession(@NotNull GameSessionCreateOrUpdateRequestDto gameSessionCreateDto, long principalId) {
		Gamer creator = gamerService.getGamerReference(principalId);
		Game game = gameService.getReferenceOfGivenGame(gameSessionCreateDto.gameId());

		LocalDate today = LocalDate.now();
		GameSession newGameSession = GameSession.builder()
			.name(gameSessionCreateDto.name())
			.visibilityType(gameSessionCreateDto.visibilityType())
			.isCompetitive(gameSessionCreateDto.isCompetitive())
			.accessType(gameSessionCreateDto.accessType())
			.date(gameSessionCreateDto.date())
			.createdAt(today)
			.modifiedAt(today)
			.numberOfMembers(1)
			.maxMembers(gameSessionCreateDto.maxMembers())
			.minAge(gameSessionCreateDto.minAge())
			.description(gameSessionCreateDto.description())
			.creator(creator)
			.game(game)
			.members(List.of(creator))
			.build();

		gameSessionCreateDto.platformsIds().forEach(platformId -> {
			Platform platform = platformService.getReferenceOfGivenPlatform(platformId);
			newGameSession.getPlatforms().add(platform);
		});

		GameSession savedNewGameSession = gameSessionRepository.save(newGameSession);
		return gameSessionMapper.toGameSessionResponseDto(savedNewGameSession, principalId);
	}

	public GameSessionPublicResponseDto updateGameSession(@NotNull GameSessionCreateOrUpdateRequestDto gameSessionCreateDto, long principalId, long gameSessionId) {
		GameSession gameSession = gameSessionRepository.findById(gameSessionId).orElseThrow(() -> new GameSessionNotFoundException(gameSessionId));
		if (!gameSession.getCreator().getId().equals(principalId)) {
			throw new TryToUpdateGameSessionWithoutRoleGameSessionOwnerException();
		}

		Game game = gameService.getReferenceOfGivenGame(gameSessionCreateDto.gameId());
		gameSession.setName(gameSessionCreateDto.name());
		gameSession.setVisibilityType(gameSessionCreateDto.visibilityType());
		gameSession.setCompetitive(gameSessionCreateDto.isCompetitive());
		gameSession.setAccessType(gameSessionCreateDto.accessType());
		gameSession.setDate(gameSessionCreateDto.date());
		gameSession.setModifiedAt(LocalDate.now());
		gameSession.setMaxMembers(gameSessionCreateDto.maxMembers());//TODO jezlie maxMembers jest mniejsze niz aktualna ilosc members to error
		gameSession.setMinAge(gameSessionCreateDto.minAge());
		gameSession.setDescription(gameSessionCreateDto.description());
		gameSession.setGame(game);

		gameSessionCreateDto.platformsIds().forEach(platformId -> {
			Platform platform = platformService.getReferenceOfGivenPlatform(platformId);
			gameSession.getPlatforms().add(platform);
		});

		GameSession updatedGameSession = gameSessionRepository.save(gameSession);
		return gameSessionMapper.toGameSessionResponseDto(updatedGameSession, principalId);
	}

	//TODO przetestować i poprawić
	public void deleteGameSession(long principalId, long gameSessionId) {
		GameSession gameSession = gameSessionRepository.findById(gameSessionId).orElseThrow(() -> new GameSessionNotFoundException(gameSessionId));
		if (!gameSession.getCreator().getId().equals(principalId)) {
			throw new TryToUpdateGameSessionWithoutRoleGameSessionOwnerException();
		}

		gameSessionRepository.deleteById(gameSessionId);
	}
}

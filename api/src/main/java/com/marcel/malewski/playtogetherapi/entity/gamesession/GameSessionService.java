package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameService;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionCreateRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.exception.GameSessionNotFoundException;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformService;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
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

	public GameSessionPublicResponseDto getGameSession(long id, long principalId) {
		GameSession gameSession = gameSessionRepository.findById(id).orElseThrow(() -> new GameSessionNotFoundException(id));
		return gameSessionMapper.toGameSessionResponseDto(gameSession, principalId);
	}

	//TODO dodać obsługe niepoprawnego enum
	public GameSessionPublicResponseDto saveGameSession(@NotNull GameSessionCreateRequestDto gameSessionCreateDto, long creatorId) {
		Gamer creator = gamerService.getGamerReference(creatorId);
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
			.build();

		newGameSession.getMembers().add(creator);
		gameSessionCreateDto.platformsIds().forEach(platformId -> {
			Platform platform = platformService.getReferenceOfGivenPlatform(platformId);
			newGameSession.getPlatforms().add(platform);
		});

		GameSession savedNewGameSession = gameSessionRepository.save(newGameSession);
		return gameSessionMapper.toGameSessionResponseDto(savedNewGameSession, creatorId);
	}
}

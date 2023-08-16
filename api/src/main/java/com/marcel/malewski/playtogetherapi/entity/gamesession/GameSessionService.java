package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionCreateRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.exception.GameSessionNotFoundException;
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
	private final GameService gameService;

	public GameSessionService(GameSessionRepository gameSessionRepository, GameSessionMapper gameSessionMapper, PlatformService platformService, GameService gameService) {
		this.gameSessionRepository = gameSessionRepository;
		this.gameSessionMapper = gameSessionMapper;
		this.platformService = platformService;
		this.gameService = gameService;
	}

	public Page<GameSessionPublicResponseDto> findAllGameSessions(@NotNull Pageable pageable) {
		return gameSessionRepository.findAll(pageable).map(gameSessionMapper::toGameSessionResponseDto);
	}

	public GameSessionPublicResponseDto getGameSession(long id) {
		GameSession gameSession = gameSessionRepository.findById(id).orElseThrow(() -> new GameSessionNotFoundException(id));
		return gameSessionMapper.toGameSessionResponseDto(gameSession);
	}


	//TODO co jak enum jest niepoprawny
	public void createGameSession(@NotNull GameSessionCreateRequestDto gameSessionCreateDto) {
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
			.build();

	}
}

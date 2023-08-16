package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionCreateRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.exception.GameSessionNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GameSessionService {
	private final GameSessionRepository gameSessionRepository;
	private final GameSessionMapper gameSessionMapper;

	public GameSessionService(GameSessionRepository gameSessionRepository, GameSessionMapper gameSessionMapper) {
		this.gameSessionRepository = gameSessionRepository;
		this.gameSessionMapper = gameSessionMapper;
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


//			today,
//			today,
//			1,
//
//		);
	}
}

package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.exception.GameSessionNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GameSessionService {
	private final GameSessionRepository gameSessionRepository;
	private final GameSessionMapper gameSessionMapper;

	public GameSessionService(GameSessionRepository gameSessionRepository, GameSessionMapper gameSessionMapper) {
		this.gameSessionRepository = gameSessionRepository;
		this.gameSessionMapper = gameSessionMapper;
	}

	public Page<GameSessionResponseDto> findAllGameSessions(Pageable pageable) {
		return gameSessionRepository.findAll(pageable).map(gameSessionMapper::toGameSessionResponseDto);
	}

	public GameSessionResponseDto getGameSession(long id) {
		GameSession gameSession = gameSessionRepository.findById(id).orElseThrow(() -> new GameSessionNotFoundException(id));
		return gameSessionMapper.toGameSessionResponseDto(gameSession);
	}
}

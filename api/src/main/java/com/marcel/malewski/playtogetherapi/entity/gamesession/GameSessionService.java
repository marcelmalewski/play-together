package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.exception.GameSessionNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameSessionService {
	private final GameSessionRepository gameSessionRepository;
	private final GameSessionMapper gameSessionMapper;

	public GameSessionService(GameSessionRepository gameSessionRepository, GameSessionMapper gameSessionMapper) {
		this.gameSessionRepository = gameSessionRepository;
		this.gameSessionMapper = gameSessionMapper;
	}

	public List<GameSessionResponseDto> findAllGameSessions() {
		return gameSessionRepository.findAll().stream().map(gameSessionMapper::toGameSessionResponseDto).toList();
	}

	public GameSessionResponseDto getGameSession(long id) {
		GameSession gameSession = gameSessionRepository.findById(id).orElseThrow(() -> new GameSessionNotFoundException(id));
		return gameSessionMapper.toGameSessionResponseDto(gameSession);
	}
}

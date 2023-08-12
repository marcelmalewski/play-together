package com.marcel.malewski.playtogetherapi.entity.gamesession;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameSessionService {
	private final GameSessionRepository gameSessionRepository;

	public GameSessionService(GameSessionRepository gameSessionRepository) {
		this.gameSessionRepository = gameSessionRepository;
	}

	public List<GameSession> findAllGameSessions() {
		return null;
	}
}

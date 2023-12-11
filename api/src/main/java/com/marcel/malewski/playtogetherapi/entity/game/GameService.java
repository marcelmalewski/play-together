package com.marcel.malewski.playtogetherapi.entity.game;

import com.marcel.malewski.playtogetherapi.entity.game.exception.GivenGameDoesNotExistException;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	private final GameRepository gameRepository;

	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	public Game getReferenceOfGivenGame(long gameId) {
		if (!gameRepository.existsById(gameId)) {
			throw new GivenGameDoesNotExistException(gameId);
		}

		return gameRepository.getReferenceById(gameId);
	}

	public Game saveGame(Game game) {
		return gameRepository.save(game);
	}
}

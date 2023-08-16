package com.marcel.malewski.playtogetherapi.entity.game;

import com.marcel.malewski.playtogetherapi.entity.game.exception.GivenGameDoesNotExistException;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	private final GameRepository gameRepository;

	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	public Game getReferenceOfGivenGame(long id) {
		if (!gameRepository.existsById(id)) {
			throw new GivenGameDoesNotExistException(id);
		}

		return gameRepository.getReferenceById(id);
	}
}

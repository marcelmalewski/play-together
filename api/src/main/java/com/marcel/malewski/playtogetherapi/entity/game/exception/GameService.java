package com.marcel.malewski.playtogetherapi.entity.game.exception;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.exception.GivenPlatformDoesNotExistException;
import org.springframework.stereotype.Service;

@Service
public class GameService {
	private final GameRepository gameRepository;

	public GameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	public Game getReferenceOfGivenGame(long id) {
		if (!gameRepository.existsById(id)) {
			throw new GivenPlatformDoesNotExistException(id);
		}

		return gameRepository.getReferenceById(id);
	}
}

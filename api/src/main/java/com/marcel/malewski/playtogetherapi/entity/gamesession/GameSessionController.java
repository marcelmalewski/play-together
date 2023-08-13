package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "v1")
@Tag(name = "Game sessions v1", description = "Game sessions API v1")
public class GameSessionController {
	private final GameSessionService gameSessionService;

	public GameSessionController(GameSessionService gameSessionService) {
		this.gameSessionService = gameSessionService;
	}

	@GetMapping(value = "/game-sessions")
	@Operation(summary = "Find all game sessions")
	public ResponseEntity<List<GameSessionResponseDto>> findAllGameSessions() {
		List<GameSessionResponseDto> allGameSessions = this.gameSessionService.findAllGameSessions();
		return new ResponseEntity<>(allGameSessions, HttpStatus.OK);
	}

	@GetMapping(value = "/game-sessions/:gameSessionId")
	@Operation(summary = "Get game session by id")
	public ResponseEntity<GameSessionResponseDto> getGameSession(long gameSessionId) {
		GameSessionResponseDto gameSession = this.gameSessionService.getGameSession(gameSessionId);
		return new ResponseEntity<>(gameSession, HttpStatus.OK);
	}
}

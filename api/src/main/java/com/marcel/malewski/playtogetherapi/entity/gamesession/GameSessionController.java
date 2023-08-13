package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.constants.GameSessionSort;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "v1")
@Tag(name = "Game sessions v1", description = "Game sessions API v1")
@Validated
public class GameSessionController {
	private final GameSessionService gameSessionService;

	public GameSessionController(GameSessionService gameSessionService) {
		this.gameSessionService = gameSessionService;
	}

	@GetMapping(value = "/game-sessions")
	@Operation(summary = "Find all game sessions")
	public ResponseEntity<List<GameSessionResponseDto>> findAllGameSessions(@RequestParam(defaultValue = "0") @Min(0) @Max(100)  int page,
	                                                                        @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
	                                                                        @RequestParam GameSessionSort sort
//	                                                                        @RequestParam String sortOrder
	) {
		//TODO defaultowe sortowanie po danie utworzenia i mozliwosc sortowanie po dacie kiedy to sie stanie
		System.out.println(sort);
		Pageable pageable = PageRequest.of(page, size);

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

package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerBasicInfoResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.GameSessionSortOption;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "v1")
@Tag(name = "Game sessions v1", description = "Game sessions API v1")
@Validated
public class GameSessionController {
	private final GameSessionService gameSessionService;
	private final GamerService gamerService;

	public GameSessionController(GameSessionService gameSessionService, GamerService gamerService) {
		this.gameSessionService = gameSessionService;
		this.gamerService = gamerService;
	}

	//TODO dodać filtr, żeby nie pokazały się te w których już jestem
	@GetMapping(value = "/game-sessions")
	@Operation(summary = "Find all game sessions")
	public ResponseEntity<Page<GameSessionPublicResponseDto>> findAllGameSessions(@RequestParam(defaultValue = "0") @Min(0) @Max(100) int page,
	                                                                              @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
	                                                                              @RequestParam(defaultValue = "CREATED_AT_DESC") GameSessionSortOption sort,
	                                                                              Principal principal, HttpServletRequest request,
	                                                                              HttpServletResponse response
	) {
		this.gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);

		Pageable pageable = PageRequest.of(page, size, sort.getSort());
		Page<GameSessionPublicResponseDto> allGameSessions = this.gameSessionService.findAllGameSessions(pageable);
		return new ResponseEntity<>(allGameSessions, HttpStatus.OK);
	}

	@GetMapping(value = "/game-sessions/:gameSessionId")
	@Operation(summary = "Get game session by id")
	public ResponseEntity<GameSessionPublicResponseDto> getGameSession(long gameSessionId, Principal principal, HttpServletRequest request,
	                                                                   HttpServletResponse response) {
		this.gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);

		GameSessionPublicResponseDto gameSession = this.gameSessionService.getGameSession(gameSessionId);
		return new ResponseEntity<>(gameSession, HttpStatus.OK);
	}

	@PostMapping(value = "/game-sessions")
	@Operation(summary = "Create a game session")
	public ResponseEntity<GamerBasicInfoResponseDto> updateGamerProfile(@Valid @RequestBody GamerBasicInfoResponseDto updateProfileDto, Principal principal, HttpServletRequest request,
	                                                                    HttpServletResponse response) {
		String gamerIdAsString = principal.getName();
		long gamerId = Long.parseLong(gamerIdAsString);

		return null;
	}
}

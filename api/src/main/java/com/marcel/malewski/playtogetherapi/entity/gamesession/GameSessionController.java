package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionCreateOrUpdateRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.GameSessionSortOption;
import com.marcel.malewski.playtogetherapi.security.util.PrincipalExtractor;
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

import static com.marcel.malewski.playtogetherapi.entity.gamesession.constants.GameSessionConstants.DEFAULT_PAGEABLE_PAGE_AS_STRING;
import static com.marcel.malewski.playtogetherapi.entity.gamesession.constants.GameSessionConstants.DEFAULT_PAGEABLE_SIZE_AS_STRING;

@RestController
@RequestMapping(path = "v1")
@Tag(name = "Game sessions v1", description = "Game sessions API v1")
@Validated
public class GameSessionController {
	private final GameSessionService gameSessionService;
	private final GamerService gamerService;
	private final PrincipalExtractor principalExtractor;

	public GameSessionController(GameSessionService gameSessionService, GamerService gamerService, PrincipalExtractor principalExtractor) {
		this.gameSessionService = gameSessionService;
		this.gamerService = gamerService;
		this.principalExtractor = principalExtractor;
	}

	//TODO endpoint not used
	@GetMapping(value = "/game-sessions")
	@Operation(summary = "Find all game sessions")
	public ResponseEntity<Page<GameSessionPublicResponseDto>> findAllGameSessions(@RequestParam(defaultValue = DEFAULT_PAGEABLE_PAGE_AS_STRING) @Min(0) @Max(100) int page,
	                                                                              @RequestParam(defaultValue = DEFAULT_PAGEABLE_SIZE_AS_STRING) @Min(1) @Max(100) int size,
	                                                                              @RequestParam(defaultValue = "CREATED_AT_DESC") GameSessionSortOption sort,
	                                                                              Principal principal, HttpServletRequest request,
	                                                                              HttpServletResponse response
	) {
//		gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);
//		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		Pageable pageable = PageRequest.of(page, size, sort.getSort());
		Page<GameSessionPublicResponseDto> allGameSessions = gameSessionService.findAllGameSessions(pageable, 1L);
		return new ResponseEntity<>(allGameSessions, HttpStatus.OK);
	}

	//TODO endpoint not used
	@GetMapping(value = "/game-sessions/{gameSessionId}")
	@Operation(summary = "Get game session by id")
	public ResponseEntity<GameSessionPublicResponseDto> getGameSession(@PathVariable long gameSessionId, Principal principal, HttpServletRequest request,
	                                                                   HttpServletResponse response) {
		gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		GameSessionPublicResponseDto gameSession = gameSessionService.getGameSession(gameSessionId, principalId);
		return new ResponseEntity<>(gameSession, HttpStatus.OK);
	}

	//TODO endpoint not used
	@PostMapping(value = "/game-sessions")
	@Operation(summary = "Create a game session")
	public ResponseEntity<GameSessionPublicResponseDto> createGameSession(@Valid @RequestBody GameSessionCreateOrUpdateRequestDto gameSessionCreateDto, Principal principal, HttpServletRequest request,
	                                                                      HttpServletResponse response) {
//		gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);
//		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		GameSessionPublicResponseDto createdGameSession = gameSessionService.createGameSession(gameSessionCreateDto, 1L);
		return new ResponseEntity<>(createdGameSession, HttpStatus.CREATED);
	}

	//TODO endpoint not used
	@PutMapping(value = "/game-sessions/{gameSessionId}")
	@Operation(summary = "Update a game session by id")
	public ResponseEntity<GameSessionPublicResponseDto> updateGameSession(@PathVariable long gameSessionId, @Valid @RequestBody GameSessionCreateOrUpdateRequestDto gameSessionCreateDto, Principal principal, HttpServletRequest request,
	                                                                      HttpServletResponse response) {
		gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);
		long principalId = principalExtractor.extractIdFromPrincipal(principal);

		GameSessionPublicResponseDto updatedGameSession = gameSessionService.updateGameSession(gameSessionCreateDto, principalId, gameSessionId);
		return new ResponseEntity<>(updatedGameSession, HttpStatus.OK);
	}

	//TODO endpoint not used
	@DeleteMapping(value = "/game-sessions/{gameSessionId}")
	public ResponseEntity<GameSessionPublicResponseDto> deleteGameSession(@PathVariable long gameSessionId, Principal principal, HttpServletRequest request,
	                                                                      HttpServletResponse response) {
		gamerService.throwExceptionAndLogoutIfAuthenticatedGamerNotFound(principal, request, response);
		long principalId = principalExtractor.extractIdFromPrincipal(principal);
		gameSessionService.deleteGameSession(principalId, gameSessionId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}

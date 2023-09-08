package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionCreateOrUpdateRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.GameSessionSortOption;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.security.util.PrincipalExtractor;
import com.marcel.malewski.playtogetherapi.util.TestGameSessionCreator;
import com.marcel.malewski.playtogetherapi.util.TestGamerCreator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.entity.gamesession.constants.GameSessionConstants.DEFAULT_PAGEABLE_PAGE;
import static com.marcel.malewski.playtogetherapi.entity.gamesession.constants.GameSessionConstants.DEFAULT_PAGEABLE_SIZE;
import static com.marcel.malewski.playtogetherapi.util.TestGameSessionCreator.getTestGameSession;
import static com.marcel.malewski.playtogetherapi.util.TestGameSessionCreator.toGameSessionResponseDto;
import static com.marcel.malewski.playtogetherapi.util.TestPlatformCreator.getTestPlatforms;
import static com.marcel.malewski.playtogetherapi.util.TestPlatformCreator.getTestPlatformsNames;
import static com.marcel.malewski.playtogetherapi.util.TestRoleCreator.getAllRoles;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameSessionController.class)
class GameSessionControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	GameSessionService gameSessionService;

	@MockBean
	PrincipalExtractor principalExtractor;

	@MockBean
	GamerService gamerService;

	private Gamer testGamer;
	private GameSession testGameSession;
	private GameSessionPublicResponseDto testGameSessionPublicResponseDto;

	@BeforeEach
	public void setup() {
		List<Platform> testPlatforms = getTestPlatforms();
		List<GamerRole> allRoles = getAllRoles();
		Game fortnite = new Game(1L, "fortnite");

		testGamer = TestGamerCreator.getTestGamer(testPlatforms, allRoles);
		testGameSession = getTestGameSession(testGamer, fortnite, testPlatforms);
		testGameSessionPublicResponseDto = toGameSessionResponseDto(testGameSession, getTestPlatformsNames(), true);
	}

	@Test
	void shouldReturnListWithOneGameSessionWhenOneGameSessionExist() throws Exception {
		List<GameSessionPublicResponseDto> allGameSessions = List.of(testGameSessionPublicResponseDto);
		Page<GameSessionPublicResponseDto> allGameSessionsAsPage = new PageImpl<>(allGameSessions);
		Pageable pageable = PageRequest.of(DEFAULT_PAGEABLE_PAGE, DEFAULT_PAGEABLE_SIZE, GameSessionSortOption.CREATED_AT_DESC.getSort());

		given(gameSessionService.findAllGameSessions(pageable, testGamer.getId())).willReturn(allGameSessionsAsPage);

		mockMvc.perform(get("/v1/game-sessions")
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.content.length()", is(1)));
	}

	@Test
	void shouldReturnGameSessionWhenGameSessionWithGivenIdExist() throws Exception {
		given(gameSessionService.getGameSession(testGameSession.getId(), testGamer.getId())).willReturn(testGameSessionPublicResponseDto);

		mockMvc.perform(get("/v1/game-sessions/" + testGameSession.getId())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(Math.toIntExact(testGameSession.getId()))))
			.andExpect(jsonPath("$.name", is(testGameSession.getName())));
	}

	//TODO poprawić nazwę
	@Test
	void testCreateGameSession() throws Exception {
		GameSessionCreateOrUpdateRequestDto gameSessionCreateOrUpdateRequestDto = TestGameSessionCreator.getGameSessionCreateOrUpdateRequestDto(testGameSession);

		given(gameSessionService.createGameSession(gameSessionCreateOrUpdateRequestDto, testGameSession.getCreator().getId())).willReturn(testGameSessionPublicResponseDto);
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());
		doNothing().when(gamerService).throwExceptionAndLogoutIfAuthenticatedGamerNotFound(any(Principal.class), any(HttpServletRequest.class), any(HttpServletResponse.class));

		mockMvc.perform(post("/v1/game-sessions/")
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gameSessionCreateOrUpdateRequestDto)))
			.andExpect(status().isCreated());
//			.andExpect(header().exists("Location"));
	}
}

package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.GameSessionSortOption;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.util.TestGamerCreator;
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

import java.util.List;

import static com.marcel.malewski.playtogetherapi.util.TestGameSessionCreator.getTestGameSession;
import static com.marcel.malewski.playtogetherapi.util.TestGameSessionCreator.toGameSessionResponseDto;
import static com.marcel.malewski.playtogetherapi.util.TestPlatformCreator.getTestPlatforms;
import static com.marcel.malewski.playtogetherapi.util.TestPlatformCreator.getTestPlatformsAsStrings;
import static com.marcel.malewski.playtogetherapi.util.TestRoleCreator.getTestRoles;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameSessionController.class)
class GameSessionControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	GameSessionService gameSessionService;

	@MockBean
	GamerService gamerService;

	private Gamer testGamer;
	private GameSession testGameSession;
	private GameSessionPublicResponseDto testGameSessionPublicResponseDto;

	@BeforeEach
	public void setup() {
		List<Platform> testPlatforms = getTestPlatforms();
		List<GamerRole> testRoles = getTestRoles();
		Game fortnite = new Game(1L, "fortnite");

		testGamer = TestGamerCreator.getTestGamer(testPlatforms, testRoles);
		testGameSession = getTestGameSession(testGamer, fortnite, testPlatforms);
		testGameSessionPublicResponseDto = toGameSessionResponseDto(testGameSession, getTestPlatformsAsStrings(), true);
	}

	@Test
	void shouldReturnListWithOneGameSessionWhenOneGameSessionExist() throws Exception {
		List<GameSessionPublicResponseDto> allGameSessions = List.of(testGameSessionPublicResponseDto);
		Page<GameSessionPublicResponseDto> allGameSessionsAsPage = new PageImpl<>(allGameSessions);
		Pageable pageable = PageRequest.of(0, 10, GameSessionSortOption.CREATED_AT_DESC.getSort());

		given(gameSessionService.findAllGameSessions(pageable, testGamer.getId())).willReturn(allGameSessionsAsPage);

		mockMvc.perform(get("/v1/game-sessions").with(user(testGamer)).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.content.length()", is(1)));
	}

	@Test
	void shouldReturnGameSessionWhenGameSessionWithGivenIdExist() throws Exception {
		given(gameSessionService.getGameSession(testGameSession.getId(), testGamer.getId())).willReturn(testGameSessionPublicResponseDto);

		mockMvc.perform(get("/v1/game-sessions/" + testGameSession.getId()).with(user(testGamer)).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(Math.toIntExact(testGameSession.getId()))))
			.andExpect(jsonPath("$.name", is(testGameSession.getName())));
	}
}

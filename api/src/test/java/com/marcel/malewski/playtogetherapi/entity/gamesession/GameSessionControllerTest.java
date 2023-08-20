package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.dto.GameSessionPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformEnum;
import com.marcel.malewski.playtogetherapi.enums.PrivacyLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

	@Test
	void getGameSessionById() throws Exception {
		LocalDate today = LocalDate.now();
		Platform pc = new Platform(1L, PlatformEnum.PC.name());
		Game fortnite = new Game(1L, "fortnite");

		Gamer testGamer = Gamer.builder()
			.id(1L)
			.login("admin")
			.password("admin.123")
			.email("admin@admin.com")
			.birthdate(LocalDate.of(2000, 1, 1))
			.playingTimeStart(LocalTime.of(15, 0))
			.playingTimeEnd(LocalTime.of(19, 0))
			.createdAt(today)
			.build();

		GameSession testGameSession = GameSession.builder()
			.id(1L)
			.name("test game session")
			.visibilityType(PrivacyLevel.PUBLIC)
			.isCompetitive(false)
			.accessType(PrivacyLevel.PUBLIC)
			.date(today)
			.createdAt(today)
			.modifiedAt(today)
			.numberOfMembers(1)
			.maxMembers(20)
			.minAge(15)
			.description("test description")
			.creator(testGamer)
			.game(fortnite)
			.build();
		testGameSession.getPlatforms().add(pc);

		GameSessionPublicResponseDto testGameSessionPublicResponseDto = new GameSessionPublicResponseDto(
			testGameSession.getId(),
			testGameSession.getName(),
			testGameSession.isCompetitive(),
			testGameSession.getAccessType(),
			testGameSession.getDate(),
			testGameSession.getCreatedAt(),
			testGameSession.getModifiedAt(),
			testGameSession.getNumberOfMembers(),
			testGameSession.getMaxMembers(),
			testGameSession.getMinAge(),
			testGameSession.getDescription(),
			testGameSession.getCreator().getLogin(),
			testGameSession.getGame().getName(),
			List.of(pc.getName()),
			true
		);


		given(gameSessionService.getGameSession(testGameSession.getId(), testGamer.getId())).willReturn(testGameSessionPublicResponseDto);
		mockMvc.perform(get("/v1/game-sessions/" + testGameSession.getId()).with(user(testGamer)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(Math.toIntExact(testGameSession.getId()))))
			.andExpect(jsonPath("$.name", is(testGameSession.getName())));
	}
}

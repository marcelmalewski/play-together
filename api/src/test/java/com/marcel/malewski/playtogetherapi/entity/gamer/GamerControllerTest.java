package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.security.util.PrincipalExtractor;
import com.marcel.malewski.playtogetherapi.security.util.SecurityHelper;
import com.marcel.malewski.playtogetherapi.util.TestGamerCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.util.TestPlatformCreator.getTestPlatforms;
import static com.marcel.malewski.playtogetherapi.util.TestRoleCreator.getAllRoles;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GamerController.class)
class GamerControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	PrincipalExtractor principalExtractor;

	@MockBean
	GamerService gamerService;

	@MockBean
	SecurityHelper securityHelper;

	private Gamer testGamer;
	private GamerPublicResponseDto testGamerPublicResponseDto;
	private GamerPrivateResponseDto testGamerPrivateResponseDto;

	@BeforeEach
	void setUp() {
		List<Platform> testPlatforms = getTestPlatforms();
		List<GamerRole> allRoles = getAllRoles();
		testGamer = TestGamerCreator.getTestGamer(testPlatforms, allRoles);
		testGamerPublicResponseDto = TestGamerCreator.toGamerPublicResponseDto(testGamer);
		testGamerPrivateResponseDto = TestGamerCreator.toGamerPrivateResponseDto(testGamer);
	}

	@Test
	void shouldReturnListWithOneGamerWhenOneGamerExist() throws Exception {
		List<GamerPublicResponseDto> allGamers = List.of(testGamerPublicResponseDto);

		given(gamerService.findAllGamersPublicInfo()).willReturn(allGamers);

		mockMvc.perform(get("/v1/gamers")
			.with(user(testGamer))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.length()", is(1)));
	}

	@Test
	void shouldReturnGamerWhenGamerWithGivenIdExist() throws Exception {
		GamerPublicResponseDto gamerPublicResponseDto  = testGamerPublicResponseDto;

		given(gamerService.getGamerPublicInfo(gamerPublicResponseDto.id())).willReturn(gamerPublicResponseDto);

		mockMvc.perform(get("/v1/gamers/" + gamerPublicResponseDto.id())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(Math.toIntExact(gamerPublicResponseDto.id()))))
			.andExpect(jsonPath("$.login", is(gamerPublicResponseDto.login())));
	}

	@Test
	void shouldReturnAuthenticatedGamerPrivateInfo() throws Exception {
		GamerPrivateResponseDto gamerPrivateResponseDto = testGamerPrivateResponseDto;

		given(gamerService.getGamerPrivateInfo(gamerPrivateResponseDto.id())).willReturn(gamerPrivateResponseDto);
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(gamerPrivateResponseDto.id());

		mockMvc.perform(get("/v1/gamers/@me")
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(Math.toIntExact(gamerPrivateResponseDto.id()))))
			.andExpect(jsonPath("$.login", is(gamerPrivateResponseDto.login())));
	}
}

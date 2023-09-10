package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthenticationDataRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.security.exception.AuthenticatedGamerNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO czy dodać test sytuacji gdy ktoś nie jest zalogowany
//TODO co jak id nie znalezione przetestowac try catche
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
		given(gamerService.getGamerPublicInfo(testGamer.getId())).willReturn(testGamerPublicResponseDto);

		mockMvc.perform(get("/v1/gamers/" + testGamer.getId())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(Math.toIntExact(testGamerPublicResponseDto.id()))))
			.andExpect(jsonPath("$.login", is(testGamerPublicResponseDto.login())));
	}

	@Test
	void shouldReturnAuthenticatedGamerPrivateInfoWhenGamerIsAuthenticated() throws Exception {
		given(gamerService.getGamerPrivateInfo(testGamer.getId())).willReturn(testGamerPrivateResponseDto);
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());

		mockMvc.perform(get("/v1/gamers/@me")
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(Math.toIntExact(testGamerPrivateResponseDto.id()))))
			.andExpect(jsonPath("$.login", is(testGamerPrivateResponseDto.login())));
	}

	@Test
	void shouldUpdateAuthenticatedGamerProfileDataWhenRequestIsValid() throws Exception {
		GamerUpdateProfileRequestDto gamerUpdateProfileRequestDto = TestGamerCreator.toGamerUpdateProfileRequestDto(testGamer);

		given(gamerService.updateGamerProfile(gamerUpdateProfileRequestDto, testGamer.getId())).willReturn(testGamerPrivateResponseDto);
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());

		mockMvc.perform(put("/v1/gamers/@me/profile-data")
				.with(csrf())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gamerUpdateProfileRequestDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(Math.toIntExact(testGamerPrivateResponseDto.id()))))
			.andExpect(jsonPath("$.login", is(testGamerPrivateResponseDto.login())));
	}

	@Test
	void updateGamerProfileShouldReturnBadRequestStatusWhenBodyIsInvalid() throws Exception {
		GamerUpdateProfileRequestDto gamerUpdateProfileRequestDto = TestGamerCreator.getInValidGamerUpdateProfileRequestDto();

		mockMvc.perform(put("/v1/gamers/@me/profile-data")
				.with(csrf())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gamerUpdateProfileRequestDto)))
			.andExpect(status().isBadRequest());
	}

	@Test
	void shouldUpdateAuthenticatedGamerAuthenticationDataWhenRequestIsValid() throws Exception {
		GamerUpdateAuthenticationDataRequestDto gamerUpdateAuthenticationDataRequestDto = TestGamerCreator.toGamerUpdateAuthenticationDataRequestDto(testGamer);

		given(gamerService.updatePartiallyGamerAuthenticationData(gamerUpdateAuthenticationDataRequestDto, testGamer.getId())).willReturn(testGamerPrivateResponseDto);
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());

		mockMvc.perform(patch("/v1/gamers/@me/authentication-data")
				.with(csrf())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gamerUpdateAuthenticationDataRequestDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(Math.toIntExact(testGamerPrivateResponseDto.id()))))
			.andExpect(jsonPath("$.login", is(testGamerPrivateResponseDto.login())));
	}

	@Test
	void updatePartiallyGamerAuthenticationDataShouldReturnBadRequestStatusWhenBodyIsInvalid() throws Exception {
		GamerUpdateAuthenticationDataRequestDto gamerUpdateAuthenticationDataRequestDto = TestGamerCreator.getInvalidGamerUpdateAuthenticationDataRequestDto();

		mockMvc.perform(patch("/v1/gamers/@me/authentication-data")
				.with(csrf())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gamerUpdateAuthenticationDataRequestDto)))
			.andExpect(status().isBadRequest());
	}

	@Test
	void shouldDeleteAuthenticatedGamer() throws Exception {
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());
		doNothing().when(gamerService).deleteGamer(testGamer.getId());

		mockMvc.perform(delete("/v1/gamers/@me")
				.with(csrf())
				.with(user(testGamer)))
			.andExpect(status().isNoContent());
	}

	@Test
	void deleteGamerShouldHandleExceptionWhenAuthenticatedGamerNotFound() throws Exception {
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());
		doThrow(GamerNotFoundException.class).when(gamerService).deleteGamer(testGamer.getId());

		mockMvc.perform(delete("/v1/gamers/@me")
				.with(csrf())
				.with(user(testGamer)))
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthenticatedGamerNotFoundException))
			.andExpect(status().isNotFound());
	}
}

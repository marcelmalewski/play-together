package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthenticationDataRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import com.marcel.malewski.playtogetherapi.entity.gamer.controller.GamerController;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.security.exception.AuthenticatedGamerNotFoundException;
import com.marcel.malewski.playtogetherapi.security.util.PrincipalExtractor;
import com.marcel.malewski.playtogetherapi.security.util.SecurityHelper;
import com.marcel.malewski.playtogetherapi.util.TestGamerCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
import java.util.Optional;

import static com.marcel.malewski.playtogetherapi.TestConstants.NUMBER_OF_GAMERS_IN_TEST_DATABASE;
import static com.marcel.malewski.playtogetherapi.entity.gamer.controller.GamerController.*;
import static com.marcel.malewski.playtogetherapi.util.TestPlatformCreator.getTestPlatforms;
import static com.marcel.malewski.playtogetherapi.util.TestRoleCreator.getModeratorRoleAsList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

	@Captor
	ArgumentCaptor<Long> longArgumentCaptor;

	private Gamer testGamer;
	private GamerPublicResponseDto testGamerPublicResponseDto;
	private GamerPrivateResponseDto testGamerPrivateResponseDto;

	@BeforeEach
	void setUp() {
		List<Platform> testPlatforms = getTestPlatforms();
		List<GamerRole> moderatorRoleAsList = getModeratorRoleAsList();
		testGamer = TestGamerCreator.getTestGamer(testPlatforms, moderatorRoleAsList);
		testGamerPublicResponseDto = TestGamerCreator.toGamerPublicResponseDto(testGamer);
		testGamerPrivateResponseDto = TestGamerCreator.toGamerPrivateResponseDto(testGamer);
	}

	@Test
	void shouldReturnPageWithGamersWhenGamersExist() throws Exception {
		List<GamerPublicResponseDto> allGamers = List.of(testGamerPublicResponseDto);
		Pageable pageable = PageRequest.of(0, NUMBER_OF_GAMERS_IN_TEST_DATABASE);
		Page<GamerPublicResponseDto> gamerPage = new PageImpl<>(allGamers, pageable, NUMBER_OF_GAMERS_IN_TEST_DATABASE);

		given(gamerService.findAllGamersPublicInfo(any(), any(), any())).willReturn(gamerPage);

		mockMvc.perform(get(GAMER_PATH_V1)
			.with(user(testGamer))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.content.size()", is(1)));
	}

	@Test
	void shouldReturnGamerWhenGamerWithGivenIdExist() throws Exception {
		given(gamerService.findGamerPublicInfo(testGamer.getId())).willReturn(Optional.of(testGamerPublicResponseDto));

		mockMvc.perform(get(GAMER_PATH_V1_ID, testGamer.getId())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(Math.toIntExact(testGamerPublicResponseDto.id()))))
			.andExpect(jsonPath("$.login", is(testGamerPublicResponseDto.login())));
	}

	@Test
	void shouldReturnGamerNotFoundWhenGamerWithGivenIdNotExist() throws Exception {
		given(gamerService.findGamerPublicInfo(testGamer.getId())).willReturn(Optional.empty());

		mockMvc.perform(get(GAMER_PATH_V1_ID, testGamer.getId())
				.with(csrf())
				.with(user(testGamer)))
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof GamerNotFoundException))
			.andExpect(status().isNotFound());
	}

	@Test
	void shouldReturnAuthenticatedGamerPrivateInfoWhenGamerIsAuthenticated() throws Exception {
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());
		given(gamerService.findGamerPrivateInfo(testGamer.getId())).willReturn(Optional.of(testGamerPrivateResponseDto));

		mockMvc.perform(get(GAMER_PATH_V1_ME)
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(Math.toIntExact(testGamerPrivateResponseDto.id()))))
			.andExpect(jsonPath("$.login", is(testGamerPrivateResponseDto.login())));
	}

	@Test
	void shouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() throws Exception {
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());
		given(gamerService.findGamerPrivateInfo(testGamer.getId())).willReturn(Optional.empty());

		mockMvc.perform(get(GAMER_PATH_V1_ME)
				.with(csrf())
				.with(user(testGamer)))
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthenticatedGamerNotFoundException))
			.andExpect(status().isNotFound());
	}

	@Test
	void shouldUpdateAuthenticatedGamerProfileDataWhenRequestIsValid() throws Exception {
		GamerUpdateProfileRequestDto gamerUpdateProfileRequestDto = TestGamerCreator.toGamerUpdateProfileRequestDto(testGamer);

		given(gamerService.tryUpdateGamerProfile(gamerUpdateProfileRequestDto, testGamer.getId())).willReturn(Optional.of(testGamerPrivateResponseDto));
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());

		mockMvc.perform(put(GAMER_PATH_V1_PROFILE_DATA)
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

		mockMvc.perform(put(GAMER_PATH_V1_PROFILE_DATA)
				.with(csrf())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gamerUpdateProfileRequestDto)))
			.andExpect(status().isBadRequest());
	}

	@Test
	void updateGamerProfileShouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() throws Exception {
		GamerUpdateProfileRequestDto gamerUpdateProfileRequestDto = TestGamerCreator.toGamerUpdateProfileRequestDto(testGamer);

		given(gamerService.tryUpdateGamerProfile(gamerUpdateProfileRequestDto, testGamer.getId())).willReturn(Optional.empty());
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());

		mockMvc.perform(put(GAMER_PATH_V1_PROFILE_DATA)
				.with(csrf())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gamerUpdateProfileRequestDto)))
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthenticatedGamerNotFoundException))
			.andExpect(status().isNotFound());
	}

	@Test
	void shouldPartiallyUpdateAuthenticatedGamerAuthenticationDataWhenRequestIsValid() throws Exception {
		GamerUpdateAuthenticationDataRequestDto gamerUpdateAuthenticationDataRequestDto = TestGamerCreator.toGamerUpdateAuthenticationDataRequestDto(testGamer, null);

		given(gamerService.tryUpdatePartiallyGamerAuthenticationData(gamerUpdateAuthenticationDataRequestDto, testGamer.getId())).willReturn(Optional.of(testGamerPrivateResponseDto));
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());

		mockMvc.perform(patch(GAMER_PATH_V1_AUTHENTICATION_DATA)
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

		mockMvc.perform(patch(GAMER_PATH_V1_AUTHENTICATION_DATA)
				.with(csrf())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gamerUpdateAuthenticationDataRequestDto)))
			.andExpect(status().isBadRequest());
	}

	@Test
	void updatePartiallyGamerAuthenticationDataShouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() throws Exception {
		GamerUpdateAuthenticationDataRequestDto gamerUpdateAuthenticationDataRequestDto = TestGamerCreator.toGamerUpdateAuthenticationDataRequestDto(testGamer, null);

		given(gamerService.tryUpdatePartiallyGamerAuthenticationData(gamerUpdateAuthenticationDataRequestDto, testGamer.getId())).willReturn(Optional.empty());
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());

		mockMvc.perform(patch(GAMER_PATH_V1_AUTHENTICATION_DATA)
				.with(csrf())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gamerUpdateAuthenticationDataRequestDto)))
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthenticatedGamerNotFoundException))
			.andExpect(status().isNotFound());
	}

	@Test
	void shouldDeleteAuthenticatedGamer() throws Exception {
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());
		given(gamerService.tryDeleteGamer(testGamer.getId())).willReturn(true);

		mockMvc.perform(delete(GAMER_PATH_V1_ME)
				.with(csrf())
				.with(user(testGamer)))
			.andExpect(status().isNoContent());

		verify(gamerService).tryDeleteGamer(longArgumentCaptor.capture());

		assertThat(testGamer.getId()).isEqualTo(longArgumentCaptor.getValue());
	}

	@Test
	void deleteGamerShouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() throws Exception {
		given(principalExtractor.extractIdFromPrincipal(any(Principal.class))).willReturn(testGamer.getId());
		given(gamerService.tryDeleteGamer(testGamer.getId())).willReturn(false);

		mockMvc.perform(delete(GAMER_PATH_V1_ME)
				.with(csrf())
				.with(user(testGamer)))
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof AuthenticatedGamerNotFoundException))
			.andExpect(status().isNotFound());
	}
}

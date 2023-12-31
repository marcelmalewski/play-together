package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthenticationDataRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
import com.marcel.malewski.playtogetherapi.entity.gamer.controller.GamerController;
import com.marcel.malewski.playtogetherapi.security.exception.AuthenticatedGamerNotFoundException;
import com.marcel.malewski.playtogetherapi.setup.DatabaseSetup;
import com.marcel.malewski.playtogetherapi.util.TestGamerCreator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import static com.marcel.malewski.playtogetherapi.TestConstants.*;
import static com.marcel.malewski.playtogetherapi.entity.gamer.controller.GamerController.GAMER_PATH_V1;
import static com.marcel.malewski.playtogetherapi.entity.gamer.controller.GamerController.GAMER_PATH_V1_PROFILE_DATA;
import static com.marcel.malewski.playtogetherapi.util.TestGamerCreator.INVALID_EMAIL;
import static com.marcel.malewski.playtogetherapi.util.TestGamerCreator.getGamerShallowCopy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;;

@SpringBootTest()
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(value = "test")
public class GamerControllerITest {
	@Container
	public static PostgreSQLContainer database = new PostgreSQLContainer(POSTGRES_IMAGE)
		.withDatabaseName("tests-db")
		.withUsername("test")
		.withPassword("test1234");

	@DynamicPropertySource
	static void setDatasourceProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", database::getJdbcUrl);
		registry.add("spring.datasource.username", () -> "test");
		registry.add("spring.datasource.password", () -> "test1234");
	}

	@Autowired
	GamerController gamerController;

	@Autowired
	GamerRepository gamerRepository;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	WebApplicationContext wac;

	MockMvc mockMvc;

	public Principal principal;
	public Gamer testGamer;

	public final String MOCK_GAMER_MANAGE_PRIVILEGE = "GAMER_MANAGE_PRIVILEGE";
	public final String MOCK_PRINCIPLE_PRIVILEGE = "PRINCIPLE_PRIVILEGE";

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		testGamer = gamerRepository.findAll().get(0);
		principal = new UsernamePasswordAuthenticationToken(testGamer, testGamer.getPassword());
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_GAMER_MANAGE_PRIVILEGE)
	@Transactional
	void shouldReturnPageWithGamersWhenGamersExist() {
		ResponseEntity<Page<GamerPublicResponseDto>> allGamersResponse = gamerController.findAllGamers(null, null, null, principal, request, response);

		assertThat(Objects.requireNonNull(allGamersResponse.getBody())).hasSize(10);
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_GAMER_MANAGE_PRIVILEGE)
	@Transactional
	void gamersFilterByNameShouldWork() throws Exception {
		mockMvc.perform(get(GAMER_PATH_V1)
				.with(csrf())
				.with(user(testGamer))
				.queryParam("gamerLogin", "Mro"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.size()", is(2)));
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_GAMER_MANAGE_PRIVILEGE)
	@Transactional
	void shouldReturnGamerWhenGamerWithGivenIdExist() {
		ResponseEntity<GamerPublicResponseDto> gamerResponse = gamerController.getGamer(testGamer.getId(), principal, request, response);

		assertThat(gamerResponse).isNotNull();
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_GAMER_MANAGE_PRIVILEGE)
	@Transactional
	void shouldReturnGamerNotFoundWhenGamerWithGivenIdNotExist() {
		assertThrows(GamerNotFoundException.class, () -> gamerController.getGamer(ID_OF_GAMER_THAT_NOT_EXIST, principal, request, response));
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_PRINCIPLE_PRIVILEGE)
	@Transactional
	void shouldReturnAuthenticatedGamerPrivateInfoWhenGamerIsAuthenticated() {
		ResponseEntity<GamerPrivateResponseDto> gamerResponse = gamerController.getGamer(principal, request, response);

		assertThat(gamerResponse).isNotNull();
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_PRINCIPLE_PRIVILEGE)
	@Transactional
	void shouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		testGamerShallowCopy.setId(ID_OF_GAMER_THAT_NOT_EXIST);
		principal = new UsernamePasswordAuthenticationToken(testGamerShallowCopy, testGamerShallowCopy.getPassword());

		assertThrows(AuthenticatedGamerNotFoundException.class, () -> gamerController.getGamer(principal, request, response));
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_PRINCIPLE_PRIVILEGE)
	@Transactional
	@Rollback
	void shouldUpdateAuthenticatedGamerProfileDataWhenRequestIsValid() {
		String updatedLogin = "updated";
		testGamer.setLogin(updatedLogin);
		GamerUpdateProfileRequestDto gamerToUpdate = TestGamerCreator.toGamerUpdateProfileRequestDto(testGamer);

		ResponseEntity<GamerPrivateResponseDto> updatedGamerResponse = gamerController.updateGamerProfile(gamerToUpdate, principal, request, response);

		assertThat(updatedGamerResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

		GamerPrivateResponseDto updatedGamer = updatedGamerResponse.getBody();
		assert updatedGamer != null;
		assertThat(updatedGamer.login()).isEqualTo(updatedLogin);
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_PRINCIPLE_PRIVILEGE)
	void updateGamerProfileShouldReturnBadRequestStatusWhenBodyIsInvalid() throws Exception {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		testGamerShallowCopy.setEmail(INVALID_EMAIL);
		GamerUpdateProfileRequestDto gamerUpdateProfileRequestDto = TestGamerCreator.toGamerUpdateProfileRequestDto(testGamerShallowCopy);

		mockMvc.perform(put(GAMER_PATH_V1_PROFILE_DATA)
				.with(csrf())
				.with(user(testGamer))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(gamerUpdateProfileRequestDto)))
			.andExpect(status().isUnprocessableEntity());
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_PRINCIPLE_PRIVILEGE)
	@Transactional
	void updateGamerProfileShouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		testGamerShallowCopy.setId(ID_OF_GAMER_THAT_NOT_EXIST);
		principal = new UsernamePasswordAuthenticationToken(testGamerShallowCopy, testGamerShallowCopy.getPassword());
		GamerUpdateProfileRequestDto gamerToUpdate = TestGamerCreator.toGamerUpdateProfileRequestDto(testGamerShallowCopy);

		assertThrows(AuthenticatedGamerNotFoundException.class, () -> gamerController.updateGamerProfile(gamerToUpdate, principal, request, response));
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_PRINCIPLE_PRIVILEGE)
	@Transactional
	@Rollback
	void shouldPartiallyUpdateAuthenticatedGamerAuthenticationDataWhenRequestIsValid() {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		String updatedEmail = "updated@updated.updated";
		testGamerShallowCopy.setEmail(updatedEmail);
		testGamerShallowCopy.setPassword(DatabaseSetup.TEST_GAMERS_PASSWORD);
		GamerUpdateAuthenticationDataRequestDto gamerUpdateAuthenticationDataRequestDto = TestGamerCreator.toGamerUpdateAuthenticationDataRequestDto(testGamerShallowCopy, null);

		ResponseEntity<GamerPrivateResponseDto> updatedGamerResponse = gamerController.updatePartiallyGamerAuthenticationData(gamerUpdateAuthenticationDataRequestDto, principal, request, response);

		assertThat(updatedGamerResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

		GamerPrivateResponseDto updatedGamer = updatedGamerResponse.getBody();
		assert updatedGamer != null;
		assertThat(updatedGamer.email()).isEqualTo(updatedEmail);
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_PRINCIPLE_PRIVILEGE)
	@Transactional
	@Rollback
	void updatePartiallyGamerAuthenticationDataShouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		testGamerShallowCopy.setId(ID_OF_GAMER_THAT_NOT_EXIST);
		principal = new UsernamePasswordAuthenticationToken(testGamerShallowCopy, testGamerShallowCopy.getPassword());
		GamerUpdateAuthenticationDataRequestDto gamerUpdateAuthenticationDataRequestDto = TestGamerCreator.toGamerUpdateAuthenticationDataRequestDto(testGamerShallowCopy, null);

		assertThrows(AuthenticatedGamerNotFoundException.class, () -> gamerController.updatePartiallyGamerAuthenticationData(gamerUpdateAuthenticationDataRequestDto, principal, request, response));
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_PRINCIPLE_PRIVILEGE)
	@Transactional
	@Rollback
	void shouldDeleteAuthenticatedGamer() {
		ResponseEntity<Void> deleteGamerResponse = gamerController.deleteGamer(principal, request, response);
		assertThat(deleteGamerResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		assertThat(gamerRepository.findById(testGamer.getId())).isEmpty();
	}

	@Test
	@WithMockUser(username = "testUser", password = "testPassword", roles = MOCK_PRINCIPLE_PRIVILEGE)
	@Transactional
	@Rollback
	void deleteGamerShouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		testGamerShallowCopy.setId(ID_OF_GAMER_THAT_NOT_EXIST);
		principal = new UsernamePasswordAuthenticationToken(testGamerShallowCopy, testGamerShallowCopy.getPassword());

		assertThrows(AuthenticatedGamerNotFoundException.class, () -> gamerController.deleteGamer(principal, request, response));
	}
}

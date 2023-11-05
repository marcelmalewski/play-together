package com.marcel.malewski.playtogetherapi.entity.gamer;


import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPrivateResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateAuthenticationDataRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerUpdateProfileRequestDto;
import com.marcel.malewski.playtogetherapi.entity.gamer.exception.GamerNotFoundException;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import static com.marcel.malewski.playtogetherapi.TestConstants.*;
import static com.marcel.malewski.playtogetherapi.util.TestGamerCreator.getGamerShallowCopy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	public Principal principal;
	public Gamer testGamer;

	@BeforeEach
	public void setUp() {
		testGamer = gamerRepository.findAll().get(0);
		principal = new UsernamePasswordAuthenticationToken(testGamer, testGamer.getPassword());
	}

	@Test
	@Transactional
	void shouldReturnListWithOneGamerWhenOneGamerExist() {
		ResponseEntity<List<GamerPublicResponseDto>> allGamersResponse = gamerController.findAllGamers(principal, request, response);

		assertThat(Objects.requireNonNull(allGamersResponse.getBody())).hasSize(NUMBER_OF_GAMERS_IN_TEST_DATABASE);
	}

	@Test
	@Transactional
	void shouldReturnGamerWhenGamerWithGivenIdExist() {
		ResponseEntity<GamerPublicResponseDto> gamerResponse = gamerController.getGamer(testGamer.getId(), principal, request, response);

		assertThat(gamerResponse).isNotNull();
	}

	@Test
	@Transactional
	void shouldReturnGamerNotFoundWhenGamerWithGivenIdNotExist() {
		assertThrows(GamerNotFoundException.class, () -> {
			gamerController.getGamer(ID_OF_GAMER_THAT_NOT_EXIST, principal, request, response);
		});
	}

	@Test
	@Transactional
	void shouldReturnAuthenticatedGamerPrivateInfoWhenGamerIsAuthenticated() {
		ResponseEntity<GamerPrivateResponseDto> gamerResponse = gamerController.getGamer(principal, request, response);

		assertThat(gamerResponse).isNotNull();
	}

	@Test
	@Transactional
	void shouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		testGamerShallowCopy.setId(ID_OF_GAMER_THAT_NOT_EXIST);
		principal = new UsernamePasswordAuthenticationToken(testGamerShallowCopy, testGamerShallowCopy.getPassword());

		assertThrows(AuthenticatedGamerNotFoundException.class, () -> {
			gamerController.getGamer(principal, request, response);
		});
	}

	@Test
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
	@Transactional
	void updateGamerProfileShouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		testGamerShallowCopy.setId(ID_OF_GAMER_THAT_NOT_EXIST);
		principal = new UsernamePasswordAuthenticationToken(testGamerShallowCopy, testGamerShallowCopy.getPassword());
		GamerUpdateProfileRequestDto gamerToUpdate = TestGamerCreator.toGamerUpdateProfileRequestDto(testGamerShallowCopy);

		assertThrows(AuthenticatedGamerNotFoundException.class, () -> {
			gamerController.updateGamerProfile(gamerToUpdate, principal, request, response);
		});
	}

	@Test
	@Transactional
	@Rollback
	void shouldPartiallyUpdateAuthenticatedGamerAuthenticationDataWhenRequestIsValid() {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		String updatedEmail = "updated@updated.updated";
		testGamerShallowCopy.setEmail(updatedEmail);
		testGamerShallowCopy.setPassword(DatabaseSetup.TEST_GAMERS_PASSWORD);
		GamerUpdateAuthenticationDataRequestDto gamerUpdateAuthenticationDataRequestDto = TestGamerCreator.toGamerUpdateAuthenticationDataRequestDto(testGamerShallowCopy);

		ResponseEntity<GamerPrivateResponseDto> updatedGamerResponse = gamerController.updatePartiallyGamerAuthenticationData(gamerUpdateAuthenticationDataRequestDto, principal, request, response);

		assertThat(updatedGamerResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

		GamerPrivateResponseDto updatedGamer = updatedGamerResponse.getBody();
		assert updatedGamer != null;
		assertThat(updatedGamer.email()).isEqualTo(updatedEmail);
	}

	@Test
	@Transactional
	@Rollback
	void updatePartiallyGamerAuthenticationDataShouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		testGamerShallowCopy.setId(ID_OF_GAMER_THAT_NOT_EXIST);
		principal = new UsernamePasswordAuthenticationToken(testGamerShallowCopy, testGamerShallowCopy.getPassword());
		GamerUpdateAuthenticationDataRequestDto gamerUpdateAuthenticationDataRequestDto = TestGamerCreator.toGamerUpdateAuthenticationDataRequestDto(testGamerShallowCopy);

		assertThrows(AuthenticatedGamerNotFoundException.class, () -> {
			gamerController.updatePartiallyGamerAuthenticationData(gamerUpdateAuthenticationDataRequestDto, principal, request, response);
		});
	}

	@Test
	@Transactional
	@Rollback
	void shouldDeleteAuthenticatedGamer() {
		ResponseEntity<Void> deleteGamerResponse = gamerController.deleteGamer(principal, request, response);
		assertThat(deleteGamerResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		assertThat(gamerRepository.findById(testGamer.getId())).isEmpty();
	}

	@Test
	@Transactional
	@Rollback
	void deleteGamerShouldThrowAuthenticatedGamerNotFoundExceptionWhenAuthenticatedGamerNotExist() {
		Gamer testGamerShallowCopy = getGamerShallowCopy(testGamer);
		testGamerShallowCopy.setId(ID_OF_GAMER_THAT_NOT_EXIST);
		principal = new UsernamePasswordAuthenticationToken(testGamerShallowCopy, testGamerShallowCopy.getPassword());

		assertThrows(AuthenticatedGamerNotFoundException.class, () -> {
			gamerController.deleteGamer(principal, request, response);
		});
	}
}

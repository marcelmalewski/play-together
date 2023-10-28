package com.marcel.malewski.playtogetherapi.entity.gamer;


import com.marcel.malewski.playtogetherapi.entity.gamer.dto.GamerPublicResponseDto;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.util.TestGamerCreator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

import static com.marcel.malewski.playtogetherapi.TestConstants.NUMBER_OF_GAMERS_IN_TEST_DATABASE;
import static com.marcel.malewski.playtogetherapi.TestConstants.POSTGRES_IMAGE;
import static com.marcel.malewski.playtogetherapi.util.TestPlatformCreator.getTestPlatforms;
import static com.marcel.malewski.playtogetherapi.util.TestRoleCreator.getAllRoles;
import static org.assertj.core.api.Assertions.assertThat;

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
		List<Platform> testPlatforms = getTestPlatforms();
		List<GamerRole> allRoles = getAllRoles();
		testGamer = TestGamerCreator.getTestGamer(testPlatforms, allRoles);
		principal = new UsernamePasswordAuthenticationToken(testGamer, testGamer.getPassword());
	}

	@Test
	@Transactional
	void shouldReturnListWithOneGamerWhenOneGamerExist() {
		ResponseEntity<List<GamerPublicResponseDto>> allGamers = gamerController.findAllGamers(principal, request, response);

		assertThat(Objects.requireNonNull(allGamers.getBody()).size()).isEqualTo(NUMBER_OF_GAMERS_IN_TEST_DATABASE);
	}

	@Test
	@Transactional
	void shouldReturnGamerWhenGamerWithGivenIdExist() {
		ResponseEntity<GamerPublicResponseDto> gamer = gamerController.getGamer(testGamer.getId(), principal, request, response);

		assertThat(gamer).isNotNull();
	}
}

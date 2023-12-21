package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.devdata.GamerCsvService;
import com.marcel.malewski.playtogetherapi.entity.game.GameService;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeService;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionMapperImpl;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionService;
import com.marcel.malewski.playtogetherapi.entity.genre.GenreService;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformService;
import com.marcel.malewski.playtogetherapi.security.util.PrincipalExtractor;
import com.marcel.malewski.playtogetherapi.security.util.SecurityHelper;
import com.marcel.malewski.playtogetherapi.setup.DatabaseTestSetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.marcel.malewski.playtogetherapi.TestConstants.POSTGRES_IMAGE;
import static com.marcel.malewski.playtogetherapi.util.TestGamerCreator.getTestGamerToSave;
import static org.assertj.core.api.Assertions.assertThat;

//TODO aktualnie jest jedna baza na wszystkie testy, zrobić baza per test?
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DatabaseTestSetup.class, GamerCsvService.class, GamerService.class, PlatformService.class, GenreService.class, GamerMapperImpl.class, GameService.class, BCryptPasswordEncoder.class, SecurityHelper.class, PrincipalExtractor.class, GamerRoleService.class, GamerPrivilegeService.class, GameSessionService.class, GameSessionMapperImpl.class})
@ActiveProfiles(value = "test")
public class GamerRepositoryTest {
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
	GamerRepository gamerRepository;

	@Autowired
	GamerService gamerService;

	@Autowired
	GamerCsvService gamerCsvService;

	@Autowired
	GamerRoleRepository gamerRoleRepository;

	@Autowired
	PlatformRepository platformRepository;

	@Autowired
	PlatformService platformService;

	@Autowired
	GenreService genreService;

	@Autowired
	GameService gameService;

	@Autowired
	GamerMapperImpl gamerMapperImpl;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	SecurityHelper securityHelper;

	@Autowired
	PrincipalExtractor principalExtractor;

	@Autowired
	GamerRoleService gamerRoleService;

	@Autowired
	GamerPrivilegeService gamerPrivilegeService;

	@Autowired
	GameSessionService gameSessionService;

	@Autowired
	GameSessionMapperImpl gameSessionMapperImpl;

	@Test
	void byLoginIsLikeIgnoreCaseShouldWork() {
		List<Gamer> gamersList = gamerRepository.findAllByLoginIsLikeIgnoreCase("%Mro%");

		assertThat(gamersList.size()).isEqualTo(2);
	}

	@Test
	void shouldSaveGamerAndJpaShouldFulfilIdAndVersion() {
		GamerRole someRole = gamerRoleRepository.getReferenceById(1L);
		List<GamerRole> roles = List.of(someRole);

		Platform somePlatform = platformRepository.getReferenceById(1L);
		List<Platform> platforms = List.of(somePlatform);

		Gamer gamerToSave = getTestGamerToSave(platforms, roles);
		Gamer savedGamer = gamerRepository.save(gamerToSave);

		gamerRepository.flush();

		assertThat(savedGamer).isNotNull();
		assertThat(savedGamer.getId()).isNotNull();
		assertThat(savedGamer.getVersion()).isNotNull();
	}
}

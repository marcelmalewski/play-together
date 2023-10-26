package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.marcel.malewski.playtogetherapi.TestConstants.POSTGRES_IMAGE;
import static com.marcel.malewski.playtogetherapi.util.TestGamerCreator.getTestGamerToSave;
import static com.marcel.malewski.playtogetherapi.util.TestPlatformCreator.getTestPlatforms;
import static com.marcel.malewski.playtogetherapi.util.TestRoleCreator.getAllRoles;
import static org.assertj.core.api.Assertions.assertThat;

//TODO aktualnie jest jedna baza na wszystkie testy, zrobić baza per test
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

	@Test
	void shouldSaveGamerAndJpaShouldFulfilIdAndVersion() {
		List<Platform> testPlatforms = getTestPlatforms();
		List<GamerRole> allRoles = getAllRoles();
		Gamer gamerToSave = getTestGamerToSave(testPlatforms, allRoles);
		Gamer savedGamer = gamerRepository.save(gamerToSave);

		assertThat(savedGamer).isNotNull();
		assertThat(savedGamer.getId()).isNotNull();
		assertThat(savedGamer.getVersion()).isNotNull();
	}
}

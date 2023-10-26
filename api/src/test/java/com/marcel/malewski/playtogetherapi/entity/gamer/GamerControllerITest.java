package com.marcel.malewski.playtogetherapi.entity.gamer;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.marcel.malewski.playtogetherapi.TestConstants.POSTGRES_IMAGE;

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

	@Test
	void testList() {
		System.out.println("test list");
	}
}

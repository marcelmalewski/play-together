package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.entity.game.GameRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.marcel.malewski.playtogetherapi.setup.DatabaseSetup.basicSetup;

@Profile("dev")
@Component
public class DatabaseDevSetup implements CommandLineRunner {
	private final GamerRepository gamerRepository;
	private final GamerRoleRepository gamerRoleRepository;
	private final PlatformRepository platformRepository;
	private final GameRepository gameRepository;
	private final GameSessionRepository gameSessionRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public DatabaseDevSetup(GamerRepository gamerRepository, GamerRoleRepository gamerRoleRepository, PlatformRepository platformRepository, GameRepository gameRepository, GameSessionRepository gameSessionRepository, BCryptPasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.gamerRoleRepository = gamerRoleRepository;
		this.platformRepository = platformRepository;
		this.gameRepository = gameRepository;
		this.gameSessionRepository = gameSessionRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		basicSetup(false, gamerRepository, gamerRoleRepository, platformRepository, passwordEncoder, gameRepository, gameSessionRepository);
	}
}

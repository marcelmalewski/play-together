package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.entity.game.GameRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.marcel.malewski.playtogetherapi.setup.DatabaseSetup.basicSetup;

@Profile("test")
@Component
public class DatabaseTestSetup implements CommandLineRunner {
	private final GamerService gamerService;
	private final GamerRoleService gamerRoleService;
	private final GamerPrivilegeRepository gamerPrivilegeRepository;
	private final PlatformRepository platformRepository;
	private final GameRepository gameRepository;
	private final GameSessionRepository gameSessionRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public DatabaseTestSetup(GamerService gamerService, GamerRoleService gamerRoleService, GamerPrivilegeRepository gamerPrivilegeRepository, PlatformRepository platformRepository, GameRepository gameRepository, GameSessionRepository gameSessionRepository, BCryptPasswordEncoder passwordEncoder) {
		this.gamerService = gamerService;
		this.gamerRoleService = gamerRoleService;
		this.gamerPrivilegeRepository = gamerPrivilegeRepository;
		this.platformRepository = platformRepository;
		this.gameRepository = gameRepository;
		this.gameSessionRepository = gameSessionRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		basicSetup(true, gamerService, gamerRoleService, platformRepository, passwordEncoder, gameRepository, gameSessionRepository, gamerPrivilegeRepository);
	}
}

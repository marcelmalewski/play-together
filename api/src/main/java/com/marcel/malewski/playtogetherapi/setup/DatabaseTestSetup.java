package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.entity.game.GameService;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformService;
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
	private final PlatformService platformService;
	private final GameService gameService;
	private final GameSessionRepository gameSessionRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public DatabaseTestSetup(GamerService gamerService, GamerRoleService gamerRoleService, GamerPrivilegeRepository gamerPrivilegeRepository, PlatformService platformService, GameService gameService, GameSessionRepository gameSessionRepository, BCryptPasswordEncoder passwordEncoder) {
		this.gamerService = gamerService;
		this.gamerRoleService = gamerRoleService;
		this.gamerPrivilegeRepository = gamerPrivilegeRepository;
		this.platformService = platformService;
		this.gameService = gameService;
		this.gameSessionRepository = gameSessionRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		basicSetup(true, gamerService, gamerRoleService, platformService, passwordEncoder, gameService, gameSessionRepository, gamerPrivilegeRepository);
	}
}

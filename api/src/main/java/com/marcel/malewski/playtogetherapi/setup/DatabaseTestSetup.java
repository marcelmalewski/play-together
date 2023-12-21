package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.devdata.GamerCsvService;
import com.marcel.malewski.playtogetherapi.entity.game.GameService;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeService;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionService;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.marcel.malewski.playtogetherapi.setup.DatabaseSetup.basicSetup;
import static com.marcel.malewski.playtogetherapi.setup.DatabaseSetup.loadTestDataFromCsv;

@Profile("test")
@Component
public class DatabaseTestSetup implements CommandLineRunner {
	private final GamerService gamerService;
	private final GamerRepository gamerRepository;
	private final GamerRoleService gamerRoleService;
	private final GamerPrivilegeService gamerPrivilegeService;
	private final PlatformService platformService;
	private final GameService gameService;
	private final GameSessionService gameSessionService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final GamerCsvService gamerCsvService;

	public DatabaseTestSetup(GamerService gamerService, GamerRepository gamerRepository, GamerRoleService gamerRoleService, GamerPrivilegeService gamerPrivilegeService, PlatformService platformService, GameService gameService, GameSessionService gameSessionService, BCryptPasswordEncoder passwordEncoder, GamerCsvService gamerCsvService) {
		this.gamerService = gamerService;
		this.gamerRepository = gamerRepository;
		this.gamerRoleService = gamerRoleService;
		this.gamerPrivilegeService = gamerPrivilegeService;
		this.platformService = platformService;
		this.gameService = gameService;
		this.gameSessionService = gameSessionService;
		this.passwordEncoder = passwordEncoder;
		this.gamerCsvService = gamerCsvService;
	}

	@Override
	public void run(String... args) {
		basicSetup(true, gamerService, gamerRoleService, platformService, passwordEncoder, gameService, gameSessionService, gamerPrivilegeService);
		loadTestDataFromCsv(gamerRepository, gamerCsvService, passwordEncoder, platformService, gamerRoleService);
	}
}

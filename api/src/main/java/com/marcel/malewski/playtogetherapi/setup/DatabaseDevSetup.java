package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.devdata.GamerCsvService;
import com.marcel.malewski.playtogetherapi.entity.game.GameService;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionService;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import static com.marcel.malewski.playtogetherapi.setup.DatabaseSetup.basicSetup;
import static com.marcel.malewski.playtogetherapi.setup.DatabaseSetup.loadTestDataFromCsv;

@Profile("dev")
@Component
public class DatabaseDevSetup implements CommandLineRunner {
	private final GamerRepository gamerRepository;
	private final GamerService gamerService;
	private final GamerRoleService gamerRoleService;
	private final GamerPrivilegeRepository gamerPrivilegeRepository;
	private final PlatformService platformService;
	private final GameService gameService;
	private final GameSessionService gameSessionService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final GamerCsvService gamerCsvService;

	public DatabaseDevSetup(GamerRepository gamerRepository, GamerService gamerService, GamerRoleService gamerRoleService, GamerPrivilegeRepository gamerPrivilegeRepository, PlatformService platformService, GameService gameService, GameSessionService gameSessionService, BCryptPasswordEncoder passwordEncoder, GamerCsvService gamerCsvService) {
		this.gamerRepository = gamerRepository;
		this.gamerService = gamerService;
		this.gamerRoleService = gamerRoleService;
		this.gamerPrivilegeRepository = gamerPrivilegeRepository;
		this.platformService = platformService;
		this.gameService = gameService;
		this.gameSessionService = gameSessionService;
		this.passwordEncoder = passwordEncoder;
		this.gamerCsvService = gamerCsvService;
	}

	@Override
	public void run(String... args) {
		basicSetup(false, gamerService, gamerRoleService, platformService, passwordEncoder, gameService, gameSessionService, gamerPrivilegeRepository);
		loadTestDataFromCsv(gamerRepository, gamerCsvService, passwordEncoder, platformService, gamerRoleService);
	}

}

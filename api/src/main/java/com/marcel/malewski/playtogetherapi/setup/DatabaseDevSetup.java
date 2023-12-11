package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.devdata.GamerCsvService;
import com.marcel.malewski.playtogetherapi.entity.game.GameRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
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
	private final GameRepository gameRepository;
	private final GameSessionRepository gameSessionRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final GamerCsvService gamerCsvService;

	public DatabaseDevSetup(GamerRepository gamerRepository, GamerService gamerService, GamerRoleService gamerRoleService, GamerPrivilegeRepository gamerPrivilegeRepository, PlatformService platformService, GameRepository gameRepository, GameSessionRepository gameSessionRepository, BCryptPasswordEncoder passwordEncoder, GamerCsvService gamerCsvService) {
		this.gamerRepository = gamerRepository;
		this.gamerService = gamerService;
		this.gamerRoleService = gamerRoleService;
		this.gamerPrivilegeRepository = gamerPrivilegeRepository;
		this.platformService = platformService;
		this.gameRepository = gameRepository;
		this.gameSessionRepository = gameSessionRepository;
		this.passwordEncoder = passwordEncoder;
		this.gamerCsvService = gamerCsvService;
	}

	@Override
	public void run(String... args) {
		basicSetup(false, gamerService, gamerRoleService, platformService, passwordEncoder, gameRepository, gameSessionRepository, gamerPrivilegeRepository);
		loadTestDataFromCsv(gamerRepository, gamerCsvService, passwordEncoder, platformService, gamerRoleService);
	}

}

package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleValue;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSession;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformEnum;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import com.marcel.malewski.playtogetherapi.enums.PrivacyLevel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

//TODO use service instead of repository?
@Profile("dev")
@Component
public class DatabaseDevSetup implements CommandLineRunner {
	private final GamerRepository gamerRepository;
	private final GamerRoleRepository gamerRoleRepository;
	private final PlatformRepository platformRepository;
	private final GameRepository gameRepository;
	private final GameSessionRepository gameSessionRepository;
	private final PasswordEncoder passwordEncoder;

	public DatabaseDevSetup(GamerRepository gamerRepository, GamerRoleRepository gamerRoleRepository, PlatformRepository platformRepository, GameRepository gameRepository, GameSessionRepository gameSessionRepository, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.gamerRoleRepository = gamerRoleRepository;
		this.platformRepository = platformRepository;
		this.gameRepository = gameRepository;
		this.gameSessionRepository = gameSessionRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		if (!gamerRepository.existsByLogin("admin")) {
			//Gamer
			Gamer admin = Gamer.builder()
				.login("admin")
				.password(passwordEncoder.encode("admin.123"))
				.email("admin@admin.com")
				.birthdate(LocalDate.of(2000, 1, 1))
				.playingTimeStart(LocalTime.of(15, 0))
				.playingTimeEnd(LocalTime.of(19, 0))
				.createdAt(LocalDate.now())
				.build();

			//Role
			GamerRole userRole = new GamerRole(GamerRoleValue.ROLE_USER.name());
			gamerRoleRepository.save(userRole);

			GamerRole moderatorRole = new GamerRole(GamerRoleValue.ROLE_MODERATOR.name());
			GamerRole savedModeratorRole = gamerRoleRepository.save(moderatorRole);
			admin.getRoles().add(savedModeratorRole);

			//Platform
			Platform pc = new Platform(PlatformEnum.PC.name());
			Platform savedPcPlatform = platformRepository.save(pc);
			admin.getPlatforms().add(savedPcPlatform);

			Gamer savedAdmin = gamerRepository.save(admin);

			//Game
			Game fortnite = new Game("fortnite");
			Game savedFortnite = gameRepository.save(fortnite);

			//GameSession
			LocalDate today = LocalDate.now();
			GameSession testGameSession = GameSession.builder()
				.name("test game session")
				.visibilityType(PrivacyLevel.PUBLIC)
				.isCompetitive(false)
				.accessType(PrivacyLevel.PUBLIC)
				.date(today)
				.createdAt(today)
				.modifiedAt(today)
				.numberOfMembers(1)
				.maxMembers(20)
				.minAge(15)
				.description("yes pls")
				.creator(savedAdmin)
				.game(savedFortnite)
				.build();

			testGameSession.getPlatforms().add(savedPcPlatform);
			gameSessionRepository.save(testGameSession);
		}
	}
}

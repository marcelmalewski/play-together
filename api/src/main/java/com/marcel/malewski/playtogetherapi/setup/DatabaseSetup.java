package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleName;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSession;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionRepository;
import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.PrivacyLevel;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformName;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public final class DatabaseSetup {
	private DatabaseSetup() {
	}

	static void BasicSetup(GamerRepository gamerRepository, GamerRoleRepository gamerRoleRepository, PlatformRepository platformRepository, PasswordEncoder passwordEncoder, GameRepository gameRepository, GameSessionRepository gameSessionRepository) {
		if (!gamerRepository.existsByLogin("admin")) {
			//Role
			GamerRole userRole = new GamerRole(GamerRoleName.ROLE_USER.name());
			gamerRoleRepository.save(userRole);

			GamerRole moderatorRole = new GamerRole(GamerRoleName.ROLE_MODERATOR.name());
			GamerRole savedModeratorRole = gamerRoleRepository.save(moderatorRole);

			//Platform
			Platform pc = new Platform(PlatformName.PC.name());
			Platform savedPcPlatform = platformRepository.save(pc);

			//Gamer
			Gamer admin = Gamer.builder()
				.login("admin")
				.password(passwordEncoder.encode("admin.123"))
				.email("admin@admin.com")
				.birthdate(LocalDate.of(2000, 1, 1))
				.playingTimeStart(LocalTime.of(15, 0))
				.playingTimeEnd(LocalTime.of(19, 0))
				.createdAt(LocalDate.now())
				.bio("Hello, I am admin.")
				.roles(List.of(savedModeratorRole))
				.platforms(List.of(savedPcPlatform))
				.build();
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
				.platforms(List.of(savedPcPlatform))
				.build();
			gameSessionRepository.save(testGameSession);
		}
	}
}

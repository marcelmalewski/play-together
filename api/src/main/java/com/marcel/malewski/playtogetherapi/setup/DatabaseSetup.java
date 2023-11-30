package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilege;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleName;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSession;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionRepository;
import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.PrivacyLevel;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.BasicPlatformName;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeName.*;

public final class DatabaseSetup {
	private DatabaseSetup() {
	}

	//TODO duzo parametrow hmmm
	static void basicSetup(boolean testSetup, GamerRepository gamerRepository, GamerRoleRepository gamerRoleRepository, PlatformRepository platformRepository, BCryptPasswordEncoder passwordEncoder, GameRepository gameRepository, GameSessionRepository gameSessionRepository, GamerPrivilegeRepository gamerPrivilegeRepository) {
		if (!gamerRepository.existsByLogin("admin")) {
			//Privilege
			//========================================================
			GamerPrivilege principlePrivilege = new GamerPrivilege(PRINCIPLE_PRIVILEGE);
			GamerPrivilege savedPrinciplePrivilege = gamerPrivilegeRepository.save(principlePrivilege);

			GamerPrivilege gamerViewPrivilege = new GamerPrivilege(GAMER_VIEW_PRIVILEGE);
			GamerPrivilege savedGamerViewPrivilege = gamerPrivilegeRepository.save(gamerViewPrivilege);


			GamerPrivilege gamerManagePrivilege = new GamerPrivilege(GAMER_MANAGE_PRIVILEGE);
			GamerPrivilege savedGamerManagePrivilege = gamerPrivilegeRepository.save(gamerManagePrivilege);

			GamerPrivilege gamerPrivateDataViewPrivilege = new GamerPrivilege(GAMER_PRIVATE_DATA_VIEW_PRIVILEGE);
			GamerPrivilege savedGamerPrivateDataViewPrivilege = gamerPrivilegeRepository.save(gamerPrivateDataViewPrivilege);


			GamerPrivilege moderatorCreatePrivilege = new GamerPrivilege(MODERATOR_CREATE_PRIVILEGE);
			GamerPrivilege savedModeratorCreatePrivilege = gamerPrivilegeRepository.save(moderatorCreatePrivilege);

			GamerPrivilege moderatorDeletePrivilege = new GamerPrivilege(MODERATOR_DELETE_PRIVILEGE);
			GamerPrivilege savedModeratorDeletePrivilege = gamerPrivilegeRepository.save(moderatorDeletePrivilege);

			//Role
			//========================================================
			List<GamerPrivilege> basicGamerRolePrivileges = List.of(savedPrinciplePrivilege, savedGamerViewPrivilege);
			GamerRole basicGamerRole = new GamerRole(GamerRoleName.BASIC_GAMER_ROLE.name());
			basicGamerRole.setGamerPrivileges(basicGamerRolePrivileges);
			gamerRoleRepository.save(basicGamerRole);

			List<GamerPrivilege> moderatorRolePrivileges = List.of(savedPrinciplePrivilege, savedGamerManagePrivilege, savedGamerPrivateDataViewPrivilege);
			moderatorRolePrivileges.addAll(basicGamerRolePrivileges);

			GamerRole moderatorRole = new GamerRole(GamerRoleName.MODERATOR_ROLE.name());
			moderatorRole.setGamerPrivileges(moderatorRolePrivileges);
			GamerRole savedModeratorRole = gamerRoleRepository.save(moderatorRole);

			List<GamerPrivilege> manageModeratorsRole = List.of(savedModeratorCreatePrivilege, savedModeratorDeletePrivilege);
			GamerRole rolesManagerRole = new GamerRole(GamerRoleName.MANAGE_MODERATORS_ROLE.name());
			rolesManagerRole.setGamerPrivileges(manageModeratorsRole);
			gamerRoleRepository.save(rolesManagerRole);

			//Platform
			//========================================================
			Platform pc = new Platform(BasicPlatformName.PC.name());
			Platform savedPcPlatform = platformRepository.save(pc);

			//Gamer
			//========================================================
			Gamer admin;

			if (testSetup) {
				admin = Gamer.builder()
					.login("test1")
					.password(passwordEncoder.encode(TEST_GAMERS_PASSWORD))
					.email("test1@test1.test1")
					.birthdate(LocalDate.of(2000, 1, 1))
					.playingTimeStart(LocalTime.of(15, 0))
					.playingTimeEnd(LocalTime.of(19, 0))
					.createdAt(LocalDate.now())
					.bio("test bio")
					.roles(List.of(savedModeratorRole))
					.platforms(List.of(savedPcPlatform))
					.build();

				Gamer testGamer2 = Gamer.builder()
					.login("test2")
					.password(passwordEncoder.encode(TEST_GAMERS_PASSWORD))
					.email("test2@test2.test2")
					.birthdate(LocalDate.of(2000, 1, 1))
					.playingTimeStart(LocalTime.of(15, 0))
					.playingTimeEnd(LocalTime.of(19, 0))
					.createdAt(LocalDate.now())
					.bio("test bio")
					.roles(List.of(savedModeratorRole))
					.platforms(List.of(savedPcPlatform))
					.build();

				gamerRepository.save(testGamer2);
			} else {
				admin = Gamer.builder()
					.login("admin")
					.password(passwordEncoder.encode(DEV_ADMIN_PASSWORD))
					.email("admin@admin.com")
					.birthdate(LocalDate.of(2000, 1, 1))
					.playingTimeStart(LocalTime.of(15, 0))
					.playingTimeEnd(LocalTime.of(19, 0))
					.createdAt(LocalDate.now())
					.bio("Hello, I am admin.")
					.roles(List.of(savedModeratorRole))
					.platforms(List.of(savedPcPlatform))
					.build();
			}

			Gamer savedAdmin = gamerRepository.save(admin);

			//Game
			//========================================================
			Game fortnite = new Game("fortnite");
			Game savedFortnite = gameRepository.save(fortnite);

			//GameSession
			//========================================================
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

	//TODO przenieść hasło do plików konfiguracyjnych?
	public static final String TEST_GAMERS_PASSWORD = "test123456789";
	public static final String DEV_ADMIN_PASSWORD = "admin.123";
}

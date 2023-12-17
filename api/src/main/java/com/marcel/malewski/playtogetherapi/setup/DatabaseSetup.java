package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.devdata.CsvFileNotFoundException;
import com.marcel.malewski.playtogetherapi.devdata.GamerCsvRecord;
import com.marcel.malewski.playtogetherapi.devdata.GamerCsvService;
import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.game.GameService;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerService;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilege;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeService;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleName;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSession;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSessionService;
import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.PrivacyLevel;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.BasicPlatformName;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilegeName.*;
import static com.marcel.malewski.playtogetherapi.validation.DateTimeParser.parseToDate;
import static com.marcel.malewski.playtogetherapi.validation.DateTimeParser.parseToTime;

public final class DatabaseSetup {
	//TODO przenieść hasło do plików konfiguracyjnych?
	public static final String TEST_GAMERS_PASSWORD = "test123456789";
	public static final String DEV_ADMIN_PASSWORD = "admin.123";

	private DatabaseSetup() {
	}

	//TODO duzo parametrow hmmm
	public static void basicSetup(boolean testSetup, GamerService gamerService, GamerRoleService gamerRoleService, PlatformService platformService, BCryptPasswordEncoder passwordEncoder, GameService gameService, GameSessionService gameSessionService, GamerPrivilegeService gamerPrivilegeService) {
		if (!gamerService.gamerExistsByLogin("admin")) {
			//Privilege
			//========================================================
			GamerPrivilege savedPrinciplePrivilege = createGamerPrivilege(PRINCIPLE_PRIVILEGE, gamerPrivilegeService);
			GamerPrivilege savedGamerViewPrivilege = createGamerPrivilege(GAMER_VIEW_PRIVILEGE, gamerPrivilegeService);

			GamerPrivilege savedGamerManagePrivilege = createGamerPrivilege(GAMER_MANAGE_PRIVILEGE, gamerPrivilegeService);

			GamerPrivilege savedModeratorManagePrivilege = createGamerPrivilege(MODERATOR_CREATE_PRIVILEGE, gamerPrivilegeService);

			//Role
			//========================================================
			List<GamerPrivilege> basicGamerRolePrivileges = List.of(savedPrinciplePrivilege, savedGamerViewPrivilege);
			GamerRole basicGamerRole = new GamerRole(GamerRoleName.BASIC_GAMER_ROLE.name());
			basicGamerRole.setGamerPrivileges(basicGamerRolePrivileges);
			gamerRoleService.saveGamerRole(basicGamerRole);

			List<GamerPrivilege> moderatorRolePrivileges = List.of(savedPrinciplePrivilege, savedGamerManagePrivilege);
			GamerRole moderatorRole = new GamerRole(GamerRoleName.MODERATOR_ROLE.name());
			moderatorRole.setGamerPrivileges(moderatorRolePrivileges);
			GamerRole savedModeratorRole = gamerRoleService.saveGamerRole(moderatorRole);

			List<GamerPrivilege> moderatorManagerRolePrivileges = List.of(savedModeratorManagePrivilege);
			GamerRole moderatorManagerRole= new GamerRole(GamerRoleName.MODERATOR_MANAGER_ROLE.name());
			moderatorManagerRole.setGamerPrivileges(moderatorManagerRolePrivileges);
			gamerRoleService.saveGamerRole(moderatorManagerRole);

			//Platform
			//========================================================
			Platform pc = new Platform(BasicPlatformName.PC.name());
			Platform savedPcPlatform = platformService.savePlatform(pc);

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
					.bio("test bio")
					.roles(List.of(savedModeratorRole))
					.platforms(List.of(savedPcPlatform))
					.build();

				gamerService.saveGamer(testGamer2);
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

			Gamer savedAdmin = gamerService.saveGamer(admin);

			//Game
			//========================================================
			Game fortnite = new Game("fortnite");
			Game savedFortnite = gameService.saveGame(fortnite);

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
			gameSessionService.saveGameSession(testGameSession);
		}
	}

	private static GamerPrivilege createGamerPrivilege(String gamerPrivilegeName, GamerPrivilegeService gamerPrivilegeService) {
		GamerPrivilege gamerPrivilege = new GamerPrivilege(gamerPrivilegeName);
		return gamerPrivilegeService.saveGamerPrivilege(gamerPrivilege);
	}

	public static void loadTestDataFromCsv(GamerRepository gamerRepository, GamerCsvService gamerCsvService, PasswordEncoder passwordEncoder, PlatformService platformService, GamerRoleService gamerRoleService) {
		if(gamerRepository.count() < 2) {
			String filePath = "classpath:gamers.csv";

			try {
				File file = ResourceUtils.getFile(filePath);

				List<GamerCsvRecord> recs = gamerCsvService.convertCSV(file, filePath);

				recs.forEach(gamerCsvRecord -> {
					String encodedPassword = passwordEncoder.encode(gamerCsvRecord.getPassword());

					LocalDate birthdate = parseToDate(gamerCsvRecord.getBirthdateAsString());
					LocalTime playingTimeStart = parseToTime(gamerCsvRecord.getPlayingTimeStartAsString());
					LocalTime playingTimeEnd = parseToTime(gamerCsvRecord.getPlayingTimeEndAsString());

					Platform platform = platformService.getReferenceOfGivenPlatform(gamerCsvRecord.getPlatformId());
					GamerRole basicGamerRole = gamerRoleService.getGamerRoleReference(GamerRoleName.BASIC_GAMER_ROLE.name());

					Gamer newGamer = Gamer.builder()
						.login(gamerCsvRecord.getLogin())
						.password(encodedPassword)
						.email(gamerCsvRecord.getEmail())
						.birthdate(birthdate)
						.playingTimeStart(playingTimeStart)
						.playingTimeEnd(playingTimeEnd)
						.platforms(List.of(platform))
						.roles(List.of(basicGamerRole))
						.build();
					gamerRepository.save(newGamer);
				});
			} catch (FileNotFoundException e) {
				throw new CsvFileNotFoundException(filePath);
			}
		}
	}
}

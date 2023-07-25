package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleEnum;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformEnum;
import com.marcel.malewski.playtogetherapi.entity.platform.PlatformRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

//TODO moze zmienic repozytoria na serwisy

@Profile("dev")
@Component
public class DatabaseDevSetup implements CommandLineRunner {
	private final GamerRepository gamerRepository;
	private final GamerRoleRepository gamerRoleRepository;
	private final PlatformRepository platformRepository;
	private final PasswordEncoder passwordEncoder;

	public DatabaseDevSetup(GamerRepository gamerRepository, GamerRoleRepository gamerRoleRepository, PlatformRepository platformRepository, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.gamerRoleRepository = gamerRoleRepository;
		this.platformRepository = platformRepository;
		this.passwordEncoder = passwordEncoder;
	}

	//TODO czy potrzebuje aż tyle ".save"
	@Override
	public void run(String... args) {
		if (!gamerRepository.existsByLogin("admin")) {
			//Gamer
			Gamer admin = new Gamer();
			admin.setLogin("admin");
			admin.setPassword(passwordEncoder.encode("admin.123"));
			admin.setEmail("admin@admin.com");
			admin.setBirthdate(LocalDate.of(2000, 1, 1));
			admin.setPlayingTimeStart(LocalTime.of(15, 0));
			admin.setPlayingTimeEnd(LocalTime.of(19, 0));
			admin.setCreatedAt(LocalDate.now());

			Gamer savedAdmin = gamerRepository.save(admin);

			//Role
			GamerRole userRole = new GamerRole();
			userRole.setName(GamerRoleEnum.USER.name());
			gamerRoleRepository.save(userRole);

			GamerRole moderatorRole = new GamerRole();
			moderatorRole.setName(GamerRoleEnum.MODERATOR.name());
			GamerRole savedModeratorRole = gamerRoleRepository.save(moderatorRole);

			savedAdmin.getRoles().add(savedModeratorRole);
			savedModeratorRole.getGamers().add(savedAdmin);
			gamerRoleRepository.save(savedModeratorRole);

			//Platform
			Platform pcPlatform = new Platform();
			pcPlatform.setName(PlatformEnum.PC.name());
			Platform savedPc = platformRepository.save(pcPlatform);

			savedAdmin.getPlatforms().add(savedPc);
			savedPc.getGamers().add(savedAdmin);
			platformRepository.save(savedPc);

			gamerRepository.save(savedAdmin);
		}
	}
}

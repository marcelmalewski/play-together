package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import com.marcel.malewski.playtogetherapi.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.gamerrole.GamerRoleEnum;
import com.marcel.malewski.playtogetherapi.gamerrole.GamerRoleRepository;
import com.marcel.malewski.playtogetherapi.platform.Platform;
import com.marcel.malewski.playtogetherapi.platform.PlatformEnum;
import com.marcel.malewski.playtogetherapi.platform.PlatformRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Profile("dev")
@Component
public class SetupDatabase implements CommandLineRunner {
	private final GamerRepository gamerRepository;
	private final GamerRoleRepository gamerRoleRepository;
	private final PlatformRepository platformRepository;
	private final PasswordEncoder passwordEncoder;

	public SetupDatabase(GamerRepository gamerRepository, GamerRoleRepository gamerRoleRepository, PlatformRepository platformRepository, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.gamerRoleRepository = gamerRoleRepository;
		this.platformRepository = platformRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		if (!gamerRepository.existsByLogin("admin")) {
			//Gamer
			Gamer admin = new Gamer();
			admin.setLogin("admin");
			admin.setPassword(passwordEncoder.encode("admin.123"));
			admin.setEmail("admin@admin.com");
			admin.setBirthDate(LocalDate.of(2000, 1, 1));
			admin.setPlayingTimeStart(LocalTime.of(15, 0));
			admin.setPlayingTimeEnd(LocalTime.of(19, 0));
			admin.setCreatedAt(LocalDate.now());

			Gamer savedAdmin = gamerRepository.save(admin);

			//Role
			GamerRole moderatorRole = new GamerRole();
			moderatorRole.setName(GamerRoleEnum.MODERATOR);
			GamerRole savedModerator = gamerRoleRepository.save(moderatorRole);

			savedAdmin.getRoles().add(savedModerator);
			savedModerator.getGamers().add(savedAdmin);

			//Platform
			Platform pcPlatform = new Platform();
			pcPlatform.setName(PlatformEnum.PC);
			Platform savedPc = platformRepository.save(pcPlatform);

			savedAdmin.getPlatforms().add(savedPc);
			savedPc.getGamers().add(savedAdmin);
		}
	}
}

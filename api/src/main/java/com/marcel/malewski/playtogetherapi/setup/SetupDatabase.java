package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class SetupDatabase implements CommandLineRunner {
	private final GamerRepository gamerRepository;

	public SetupDatabase(GamerRepository gamerRepository) {
		this.gamerRepository = gamerRepository;
	}

	@Override
	public void run(String... args) {
//		if (!gamerRepository.existsByLogin("admin")) {
//			Gamer admin = new Gamer();
//			admin.setLogin("admin");
//			admin.setPassword("admin-pw");
//			admin.setEmail("yes@yes.com");
//			admin.setBirthDate(LocalDate.of(2000, 1, 1));
//			admin.setPlayingTimeStart(LocalTime.of(15, 0));
//			admin.setPlayingTimeEnd(LocalTime.of(19, 0));
//			admin.setPlatforms(List.of(Platform.PC));
//			admin.setRole(Role.MODERATOR);
//			admin.setCreatedAt(LocalDate.now());
//
//			gamerRepository.save(admin);
//		}
	}
}

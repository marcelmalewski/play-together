package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class SetupDatabase implements CommandLineRunner {
	private final GamerRepository gamerRepository;
	private final PasswordEncoder passwordEncoder;

	public SetupDatabase(GamerRepository gamerRepository, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
//		if (!gamerRepository.existsByLogin("admin")) {
//			Gamer admin = new Gamer();
//			admin.setLogin("admin");
//			admin.setPassword(passwordEncoder.encode("admin.123"));
//			admin.setEmail("admin@admin.com");
//			admin.setBirthDate(LocalDate.of(2000, 1, 1));
//			admin.setPlayingTimeStart(LocalTime.of(15, 0));
//			admin.setPlayingTimeEnd(LocalTime.of(19, 0));
//			admin.setPlatformEnums(List.of(PlatformEnum.PC));
//			admin.setRole(GamerRoleEnum.MODERATOR);
//			admin.setCreatedAt(LocalDate.now());
//
//			gamerRepository.save(admin);
//		}

//		Book noEJBSaved = bookRepository.save(noEJB);
//
//		ericSaved.getBooks().add(dddSaved);
	}
}

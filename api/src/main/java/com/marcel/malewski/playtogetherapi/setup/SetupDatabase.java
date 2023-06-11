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
//		if (!personRepository.existsByLogin("admin")) {
//			Person person = new Person();
//			person.setLogin("admin");
//			person.setPassword(passwordEncoder.encode("admin.123"));
//			person.setEmail("asdf");
//			person.setGamerRole(PersonRole.ROLE_MODERATOR);
//
//			personRepository.save(person);
//		}

		//		if (!gamerRepository.existsByLogin("admin")) {
//			Gamer admin = new Gamer();
//			admin.setLogin("admin");
//			admin.setPassword("admin-pw");
//			admin.setEmail("yes@yes.com");
//			admin.setBirthDate(LocalDate.of(2000, 1, 1));
//			admin.setPlayingTimeStart(LocalTime.of(15, 0));
//			admin.setPlayingTimeEnd(LocalTime.of(19, 0));
//			admin.setPlatforms(List.of(Platform.PC));
//			admin.setGamerRole(GamerRole.MODERATOR);
//			admin.setCreatedAt(LocalDate.now());
//
//			gamerRepository.save(admin);
//		}
	}
}

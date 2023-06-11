package com.marcel.malewski.playtogetherapi.setup;

import com.marcel.malewski.playtogetherapi.person.Person;
import com.marcel.malewski.playtogetherapi.person.PersonRepository;
import com.marcel.malewski.playtogetherapi.person.PersonRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class SetupDatabase implements CommandLineRunner {
	private final PersonRepository personRepository;
	private final PasswordEncoder passwordEncoder;

	public SetupDatabase(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
		this.personRepository = personRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		if (!personRepository.existsByLogin("admin")) {
			Person person = new Person();
			person.setLogin("admin");
			person.setPassword(passwordEncoder.encode("admin.123"));
			person.setEmail("asdf");
			person.setRole(PersonRole.ROLE_MODERATOR);

			personRepository.save(person);
		}
	}
}

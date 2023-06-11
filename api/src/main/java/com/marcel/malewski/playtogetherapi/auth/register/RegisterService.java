package com.marcel.malewski.playtogetherapi.auth.register;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
	private final PersonRepository personRepository;
	private final PasswordEncoder passwordEncoder;

	public RegisterService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
		this.personRepository = personRepository;
		this.passwordEncoder = passwordEncoder;
	}

	void registerPerson() {

		if (!personRepository.existsByLogin("test")) {
			Person person = new Person();
			person.setLogin("test");
			person.setPassword(passwordEncoder.encode("test.123"));
			person.setEmail("asdf");
			person.setRole(PersonRole.ROLE_MODERATOR);

			System.out.println(person);
			this.personRepository.save(person);
		}
	}

}

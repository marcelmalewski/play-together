package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.person.Person;
import com.marcel.malewski.playtogetherapi.person.PersonRepository;
import com.marcel.malewski.playtogetherapi.person.PersonRole;
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
		Person person = new Person();
		person.setLogin("test");
		person.setPassword(passwordEncoder.encode("test.123"));
		person.setEmail("asdf");
		person.setRole(PersonRole.ROLE_MODERATOR);

		personRepository.save(person);
	}
}

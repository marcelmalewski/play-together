package com.marcel.malewski.playtogetherapi.auth.register;

import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
	private final GamerRepository gamerRepository;
	private final PasswordEncoder passwordEncoder;

	public RegisterService(GamerRepository gamerRepository, PasswordEncoder passwordEncoder) {
		this.gamerRepository = gamerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	void registerPerson() {

//		if (!personRepository.existsByLogin("test")) {
//			Person person = new Person();
//			person.setLogin("test");
//			person.setPassword(passwordEncoder.encode("test.123"));
//			person.setEmail("asdf");
//			person.setRole(PersonRole.ROLE_MODERATOR);
//
//			System.out.println(person);
//			this.personRepository.save(person);
//		}
	}

}

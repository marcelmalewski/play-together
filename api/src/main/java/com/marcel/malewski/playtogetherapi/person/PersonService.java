package com.marcel.malewski.playtogetherapi.person;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonService {
	private final PersonRepository personRepository;

	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public List<Person> findAllUsers() {
		return personRepository.findAll();
	}
}

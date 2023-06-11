package com.marcel.malewski.playtogetherapi.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PostgresUserDetailsService implements UserDetailsService {
	private final PersonRepository personRepository;

	public PostgresUserDetailsService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		return personRepository.findByLogin(login).get();
	}
}

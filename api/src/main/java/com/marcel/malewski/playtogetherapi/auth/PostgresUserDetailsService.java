package com.marcel.malewski.playtogetherapi.auth;

import com.marcel.malewski.playtogetherapi.gamer.GamerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PostgresUserDetailsService implements UserDetailsService {
	private final GamerRepository gamerRepository;

	public PostgresUserDetailsService(GamerRepository gamerRepository) {
		this.gamerRepository = gamerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String loginOrEmail) throws UsernameNotFoundException {
		return gamerRepository.findByLoginOrEmail(loginOrEmail, loginOrEmail).get();
	}
}

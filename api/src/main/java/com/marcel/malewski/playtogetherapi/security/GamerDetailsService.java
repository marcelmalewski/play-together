package com.marcel.malewski.playtogetherapi.security;

import com.marcel.malewski.playtogetherapi.entity.gamer.GamerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GamerDetailsService implements UserDetailsService {
	private final GamerRepository gamerRepository;

	public GamerDetailsService(GamerRepository gamerRepository) {
		this.gamerRepository = gamerRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String loginOrEmail) {
		return gamerRepository.findByLoginOrEmail(loginOrEmail, loginOrEmail).orElseThrow(() -> new UsernameNotFoundException(loginOrEmail));
	}
}

package com.marcel.malewski.playtogetherapi.gamer;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamerService {
	private final GamerRepository gamerRepository;

	public GamerService(GamerRepository gamerRepository) {
		this.gamerRepository = gamerRepository;
	}

	public List<Gamer> findAllGamers() {
		return gamerRepository.findAll();
	}
}

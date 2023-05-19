package com.marcel.malewski.playtogetherapi.gamer;

import org.springframework.stereotype.Service;

@Service
public class GamerServiceImpl implements GamerService {
	private final GamerRepository gamerRepository;

	public GamerServiceImpl(GamerRepository gamerRepository) {
		this.gamerRepository = gamerRepository;
	}

	@Override
	public Iterable<Gamer> findAll() {
		return gamerRepository.findAll();
	}
}

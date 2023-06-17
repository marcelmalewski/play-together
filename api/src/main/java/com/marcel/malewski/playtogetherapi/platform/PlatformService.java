package com.marcel.malewski.playtogetherapi.platform;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformService {
	private final PlatformRepository platformRepository;

	public PlatformService(PlatformRepository platformRepository) {
		this.platformRepository = platformRepository;
	}

	public List<Platform> findAllPlatforms() {
		return platformRepository.findAll();
	}
}

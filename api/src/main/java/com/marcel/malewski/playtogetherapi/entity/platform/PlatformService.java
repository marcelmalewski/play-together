package com.marcel.malewski.playtogetherapi.entity.platform;

import com.marcel.malewski.playtogetherapi.entity.platform.exception.GivenPlatformDoesNotExistException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class PlatformService {
	private final PlatformRepository platformRepository;

	public PlatformService(PlatformRepository platformRepository) {
		this.platformRepository = platformRepository;
	}

	public List<Platform> findAllPlatforms() {
		return platformRepository.findAll();
	}

	public Platform getReferenceOfGivenPlatform(long platformId) {
		if (!platformRepository.existsById(platformId)) {
			throw new GivenPlatformDoesNotExistException(platformId);
		}

		return platformRepository.getReferenceById(platformId);
	}

	public Platform savePlatform(@NotNull Platform platform) {
		return platformRepository.save(platform);
	}
}

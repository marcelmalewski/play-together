package com.marcel.malewski.playtogetherapi.entity.platform;

import com.marcel.malewski.playtogetherapi.entity.platform.exception.GivenPlatformDoesNotExistException;
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

	public Platform getPlatformReference(long id) {
		return platformRepository.getReferenceById(id);
	}

	public Platform getReferenceOfGivenPlatform(long id) {
		if (!platformRepository.existsById(id)) {
			throw new GivenPlatformDoesNotExistException(id);
		}

		return platformRepository.getReferenceById(id);
	}

	public void throwExceptionIfGivenPlatformDoesNotExist(List<Long> platforms) {
		platforms.forEach(platformId -> {
			if (!platformRepository.existsById(platformId)) {
				throw new GivenPlatformDoesNotExistException(platformId);
			}
		});
	}
}

package com.marcel.malewski.playtogetherapi.entity.gamerrole;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.exception.GamerRoleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GamerRoleService {
	private final GamerRoleRepository gamerRoleRepository;

	public GamerRoleService(GamerRoleRepository gamerRoleRepository) {
		this.gamerRoleRepository = gamerRoleRepository;
	}

	public GamerRole getGamerRoleReference(String name) {
		if (!gamerRoleRepository.existsByName(name)) {
			throw new GamerRoleNotFoundException(name);
		}

		return gamerRoleRepository.getReferenceByName(name);
	}
}

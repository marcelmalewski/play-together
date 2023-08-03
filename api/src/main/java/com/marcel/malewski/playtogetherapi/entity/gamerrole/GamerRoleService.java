package com.marcel.malewski.playtogetherapi.entity.gamerrole;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.exception.GamerRoleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GamerRoleService {
	private final GamerRoleRepository gamerRoleRepository;

	public GamerRoleService(GamerRoleRepository gamerRoleRepository) {
		this.gamerRoleRepository = gamerRoleRepository;
	}

	//TODO to jest nieużywane
	public GamerRole getGamerRole(String name) {
		return gamerRoleRepository.findByName(name).orElseThrow(() -> new GamerRoleNotFoundException(name));
	}

	public GamerRole getGamerRoleReference(String name) {
		if (!gamerRoleRepository.existsByName(name)) {
			throw new GamerRoleNotFoundException(name);
		}

		return gamerRoleRepository.getReferenceByName(name);
	}
}

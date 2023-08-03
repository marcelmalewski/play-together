package com.marcel.malewski.playtogetherapi.entity.gamerrole;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.exception.GamerRoleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GamerRoleService {
	private final GamerRoleRepository gamerRoleRepository;

	public GamerRoleService(GamerRoleRepository gamerRoleRepository) {
		this.gamerRoleRepository = gamerRoleRepository;
	}

	//TODO poprawić get
	public GamerRole getGamerRole(String name) {
		return gamerRoleRepository.findByName(name).get();
	}

	public GamerRole getGamerRoleReference(String name) {
		if (!gamerRoleRepository.existsById(id)) {
			throw new GamerRoleNotFoundException(id);
		}

		return gamerRoleRepository.getReferenceByName(name);
	}
}

package com.marcel.malewski.playtogetherapi.entity.gamerrole;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.exception.GamerRoleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GamerRoleService {
	private final GamerRoleRepository gamerRoleRepository;

	public GamerRoleService(GamerRoleRepository gamerRoleRepository) {
		this.gamerRoleRepository = gamerRoleRepository;
	}

	public GamerRole getGamerRoleReference(String gamerRoleName) {
		if (!gamerRoleRepository.existsByName(gamerRoleName)) {
			throw new GamerRoleNotFoundException(gamerRoleName);
		}

		return gamerRoleRepository.getReferenceByName(gamerRoleName);
	}
}

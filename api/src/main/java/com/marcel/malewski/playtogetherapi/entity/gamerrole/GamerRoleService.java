package com.marcel.malewski.playtogetherapi.entity.gamerrole;

import org.springframework.stereotype.Service;

@Service
public class GamerRoleService {
	private final GamerRoleRepository gamerRoleRepository;

	public GamerRoleService(GamerRoleRepository gamerRoleRepository) {
		this.gamerRoleRepository = gamerRoleRepository;
	}

	public GamerRole getGamerRole(String name) {
		return gamerRoleRepository.findByName(name).get();
	}
}

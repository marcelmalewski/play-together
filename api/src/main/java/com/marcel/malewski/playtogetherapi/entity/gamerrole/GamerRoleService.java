package com.marcel.malewski.playtogetherapi.entity.gamerrole;

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

	public GamerRole getGamerRoleReference(long id) {
		return gamerRoleRepository.getReferenceById(id);
	}
}

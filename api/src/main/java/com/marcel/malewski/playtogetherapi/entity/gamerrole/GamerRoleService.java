package com.marcel.malewski.playtogetherapi.entity.gamerrole;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.exception.GamerRoleNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class GamerRoleService {
	private final GamerRoleRepository gamerRoleRepository;

	public GamerRoleService(GamerRoleRepository gamerRoleRepository) {
		this.gamerRoleRepository = gamerRoleRepository;
	}

	public GamerRole getGamerRoleReference(@NotNull String gamerRoleName) {
		if (!gamerRoleRepository.existsByName(gamerRoleName)) {
			throw new GamerRoleNotFoundException(gamerRoleName);
		}

		return gamerRoleRepository.getReferenceByName(gamerRoleName);
	}
}

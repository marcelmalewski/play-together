package com.marcel.malewski.playtogetherapi.entity.gamerprivilege;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class GamerPrivilegeService {
	private final GamerPrivilegeRepository gamerPrivilegeRepository;

	public GamerPrivilegeService(GamerPrivilegeRepository gamerPrivilegeRepository) {
		this.gamerPrivilegeRepository = gamerPrivilegeRepository;
	}

	public GamerPrivilege saveGamerPrivilege(@NotNull GamerPrivilege gamerPrivilege) {
		return gamerPrivilegeRepository.save(gamerPrivilege);
	}
}

package com.marcel.malewski.playtogetherapi.util;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleName;

import java.util.List;

public final class TestRoleCreator {
	private TestRoleCreator() {
	}

	public static List<GamerRole> getAllRoles() {
		GamerRole userRole = new GamerRole(1L, GamerRoleName.ROLE_USER.name());
		GamerRole moderatorRole = new GamerRole(1L, GamerRoleName.ROLE_MODERATOR.name());
		return List.of(userRole, moderatorRole);
	}
}

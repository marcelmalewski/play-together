package com.marcel.malewski.playtogetherapi.util;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleValue;

import java.util.List;

public final class TestRoleCreator {
	private TestRoleCreator() {
	}

	public static List<GamerRole> getAllRoles() {
		GamerRole userRole = new GamerRole(1L, GamerRoleValue.ROLE_USER.name());
		GamerRole moderatorRole = new GamerRole(1L, GamerRoleValue.ROLE_MODERATOR.name());
		return List.of(userRole, moderatorRole);
	}
}

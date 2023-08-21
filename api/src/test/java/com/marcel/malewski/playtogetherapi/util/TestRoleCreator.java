package com.marcel.malewski.playtogetherapi.util;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleValue;

import java.util.List;

public final class TestRoleCreator {
	private TestRoleCreator() {
	}

	public static List<GamerRole> getTestRoles() {
		GamerRole userRole = new GamerRole(1L, GamerRoleValue.ROLE_USER.name());
		return List.of(userRole);
	}
}

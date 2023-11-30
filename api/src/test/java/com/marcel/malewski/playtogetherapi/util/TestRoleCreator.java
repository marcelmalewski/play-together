package com.marcel.malewski.playtogetherapi.util;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRoleName;

import java.util.List;

public final class TestRoleCreator {
	private TestRoleCreator() {
	}

	public static List<GamerRole> getModeratorRoleAsList() {
		GamerRole moderatorRole = new GamerRole(1L, GamerRoleName.MODERATOR_ROLE.name());
		return List.of(moderatorRole);
	}
}

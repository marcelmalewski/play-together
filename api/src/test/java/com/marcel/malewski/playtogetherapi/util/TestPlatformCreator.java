package com.marcel.malewski.playtogetherapi.util;

import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.entity.platform.BasicPlatformName;

import java.util.List;

public final class TestPlatformCreator {
	private static final String PLATFORM_NAME = BasicPlatformName.PC.name();
	private static final long PLATFORM_ID = 1L;

	private TestPlatformCreator() {
	}

	public static List<Platform> getTestPlatforms() {
		Platform pc = new Platform(PLATFORM_ID, PLATFORM_NAME);
		return List.of(pc);
	}

	public static List<String> getTestPlatformsNames() {
		return List.of(PLATFORM_NAME);
	}
}

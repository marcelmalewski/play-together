package com.marcel.malewski.playtogetherapi.testObject;

import com.marcel.malewski.playtogetherapi.interfaces.EntityWithPlayingTimeAsString;
import com.marcel.malewski.playtogetherapi.validation.playingtime.ValidPlayingTime;

@ValidPlayingTime
public record PlayingTimeTestObject(
	String playingTimeStartAsString,
	String playingTimeEndAsString
) implements EntityWithPlayingTimeAsString {

	@Override
	public String getPlayingTimeStartAsString() {
		return playingTimeStartAsString;
	}

	@Override
	public String getPlayingTimeEndAsString() {
		return playingTimeEndAsString;
	}
}

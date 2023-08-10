package com.marcel.malewski.playtogetherapi.validation;

import com.marcel.malewski.playtogetherapi.security.register.GamerRegisterRequestDto;
import jakarta.validation.constraints.NotNull;

import static com.marcel.malewski.playtogetherapi.validation.ValidationConstants.*;

public final class ValidGamerRegisterRequestDto {
	private ValidGamerRegisterRequestDto() {
	}

	public static @NotNull GamerRegisterRequestDto getValidGamerRegisterRequestDto() {
		return new GamerRegisterRequestDto(
			LOGIN,
			PASSWORD,
			EMAIL,
			BIRTH_DATE,
			PLAYING_TIME_NINE_O_CLOCK,
			PLAYING_TIME_TEN_O_CLOCK,
			PLATFORMS_IDS
		);
	}
}

package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record GamerUpdateAuthenticationDataRequestDto(
	@Email
	String email,
	String newPassword,
	@NotBlank
	String currentPassword
) {
}

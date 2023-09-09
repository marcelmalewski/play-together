package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//TODO dodać walidacje, obowiązkowe jest tylko currentPassword
public record GamerUpdateAuthenticationDataRequestDto(
	@Email
	@NotNull
	String email,
	@Size(min = 8, max = 30)
	@NotBlank
	String newPassword,
	@NotBlank
	String currentPassword
) {
}

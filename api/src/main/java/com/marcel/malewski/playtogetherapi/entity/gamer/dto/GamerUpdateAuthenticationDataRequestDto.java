package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//TODO mandatory is only currentPassword
//TODO newPassword and email can be null
public record GamerUpdateAuthenticationDataRequestDto(
	@Email
	String email,
	String newPassword,
	@NotBlank
	String currentPassword
) {
}

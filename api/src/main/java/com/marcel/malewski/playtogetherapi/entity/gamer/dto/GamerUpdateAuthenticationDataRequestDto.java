package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//TODO obowiązkowe jest tylko currentPassword
public record GamerUpdateAuthenticationDataRequestDto(
	@Email
	String email,
	//TODO dodać większe wymagania hasła, ale też, że może być nullem
	String newPassword,
	@NotBlank
	String currentPassword
) {
}

package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//TODO obowiązkowe jest tylko currentPassword
public record GamerUpdateAuthenticationDataRequestDto(
	@Email
	String email,
	//TODO dodać, że hasło może być nullem ,ale jak nie jest to ma  większe wymagania hasła, ale też
	String newPassword,
	@NotBlank
	String currentPassword
) {
}

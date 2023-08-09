package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

//TODO dodać walidacje, obowiązkowe jest tylko currentPassword
public record GamerUpdateAuthenticationDataRequestDto(
	String email,
	String newPassword,
	String currentPassword
) {
}

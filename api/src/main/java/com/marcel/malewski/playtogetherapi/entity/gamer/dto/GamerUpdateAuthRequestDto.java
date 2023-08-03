package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

//TODO dodać walidacje, obowiązkowe jest tylko currentPassword
public record GamerUpdateAuthRequestDto(
	String email,
	String newPassword,
	String currentPassword
) {
}

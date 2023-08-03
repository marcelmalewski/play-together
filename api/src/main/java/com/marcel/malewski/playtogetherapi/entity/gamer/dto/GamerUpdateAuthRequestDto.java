package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

//TODO dodać walidacje
public record GamerUpdateAuthRequestDto(
	String email,
	String newPassword,
	String currentPassword
) {
}

package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

public record GamerUpdateAuthRequestDto(
	String email,
	String newPassword,
	String currentPassword
) {
}

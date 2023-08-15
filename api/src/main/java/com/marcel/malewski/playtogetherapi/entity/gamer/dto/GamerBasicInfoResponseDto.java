package com.marcel.malewski.playtogetherapi.entity.gamer.dto;

public record GamerBasicInfoResponseDto(
	long id,
	String login,
	String avatarUrl
) {
}

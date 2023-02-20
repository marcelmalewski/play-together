package com.marcel.malewski.playtogetherapi.gamer.dto;

public record GamerCreateRequestDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String login,
        String nickname,
        String password
) {
}

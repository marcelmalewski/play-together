package com.marcel.malewski.playtogetherapi.gamer.dto;

//jezeli GamerCreateRequestDto niczym sie nie rozni to mamy duplikat
public record GamerUpdateRequestDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String login,
        String nickname,
        String password
) {
}

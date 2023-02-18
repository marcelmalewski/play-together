package com.marcel.malewski.playtogetherapi.gamer.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GamerUpdateRequestDto {
   private Long id;
   private String firstName;
   private String lastName;
   private String email;
   private String login;
   private String nickname;
   private String password;
}

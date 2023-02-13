package com.marcel.malewski.playtogetherapi.gamer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Gamer {
   private Long id;
   private String firstName;
   private String lastName;
   private String email;
   private String login;
   private String nickname;
   private String password;
}

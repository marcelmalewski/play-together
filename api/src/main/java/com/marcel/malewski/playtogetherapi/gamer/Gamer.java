package com.marcel.malewski.playtogetherapi.gamer;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "gamer")
public class Gamer {
   @Id
   @SequenceGenerator(name = "gamer_sequence", sequenceName = "gamer_sequence", allocationSize = 1)
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamer_sequence")
   private Long id;
   private String firstName;
   private String lastName;
   private String email;
   private String login;
   private String nickname;
   private String password;
}

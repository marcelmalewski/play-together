package com.marcel.malewski.playtogetherapi.gamer;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

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

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      Gamer gamer = (Gamer) o;
      return id != null && Objects.equals(id, gamer.id);
   }

   @Override
   public int hashCode() {
      return getClass().hashCode();
   }
}

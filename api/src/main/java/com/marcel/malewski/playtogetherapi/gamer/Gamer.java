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
   @Column(nullable = false)
   private String firstName;
   @Column(nullable = false)
   private String lastName;
   private String email;
   @Column(nullable = false)
   private String login;
   private String nickname;
   @Column(nullable = false)
   private String password;

   public Gamer(Long id, String firstName, String lastName, String login, String nickname, String password) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.login = login;
      this.nickname = nickname;
      this.password = password;
   }

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

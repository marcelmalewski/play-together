package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamesession.GameSession;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "gamer")
//TODO dodać co moze byc nullem
public class Gamer {
	@Id
	@SequenceGenerator(name = "gamer_sequence", sequenceName = "gamer_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamer_sequence")
	private Long id;
	private String login;
	private String password;
	private String email;
	private Date birthdate;
	private String bio;
	private String avatarUrl;
	private String playingTimeStart;
	private String playingTimeEnd;
	@ManyToMany(mappedBy = "gamers")
	@ToString.Exclude
	private Set<GameSession> gameSessions = new HashSet<>();
	//   @Column(nullable = false)
	//   private String password;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Gamer gamer)) return false;

		return getId() != null ? getId().equals(gamer.getId()) : gamer.getId() == null;
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}

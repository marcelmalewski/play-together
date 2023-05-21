package com.marcel.malewski.playtogetherapi.gamesession;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "game_session")
public class GameSession {
	@Id
	@SequenceGenerator(name = "game_sequence", sequenceName = "game_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_sequence")
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private String game;
	private String description;
	@NotNull
	private String visibilityType;
	@NotNull
	private String accessType;
	@NotNull
	private String date;
	private boolean isCompetitive;
	private int maxMembers;
	private int minAge;
	private String availabilityTimes;
	private String pendingMembers;
	private int numberOfMembers;
	private boolean isCurrentUserMember;
	@ManyToOne
	@JoinColumn(name = "gamer_id")
	@NotNull
	private Gamer creator;
	@ManyToMany
	@JoinTable(name = "gamer_game_session",
					joinColumns = @JoinColumn(name = "game_session_id"),
					inverseJoinColumns = @JoinColumn(name = "gamer_id"))
	@ToString.Exclude
	@NotNull
	private Set<Gamer> members = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GameSession gameSession)) return false;

		return getId() != null ? getId().equals(gameSession.getId()) : gameSession.getId() == null;
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}

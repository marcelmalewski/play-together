package com.marcel.malewski.playtogetherapi.gamesession;

import com.marcel.malewski.playtogetherapi.game.Game;
import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.shared.PrivacyLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "game_session")
public class GameSession {
	@Id
	@SequenceGenerator(name = "game_session_sequence", sequenceName = "game_session_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_session_sequence")
	private Long id;
	@NotNull
	private String name;
	@Enumerated(EnumType.STRING)
	@NotNull
	private PrivacyLevel visibilityType;
	@Enumerated(EnumType.STRING)
	@NotNull
	private PrivacyLevel accessType;
	@NotNull
	private LocalDate date;
	@NotNull//TODO to na potem
	private String availabilityTimes;

	private int numberOfMembers;
	private boolean isCurrentUserMember;
	private String description;
	private boolean isCompetitive;
	private int maxMembers;
	private int minAge;
	private String pendingMembers;//TODO to na potem

	@ManyToOne
	@JoinColumn(name = "gamer_id")
	@NotNull
	private Gamer creator;
	@ManyToMany
	@JoinTable(name = "game_session_members",
					joinColumns = @JoinColumn(name = "game_session_id"),
					inverseJoinColumns = @JoinColumn(name = "gamer_id"))
	@ToString.Exclude
	@NotNull
	private List<Gamer> members = new LinkedList<>();
	@ManyToOne
	@JoinColumn(name = "game_id")
	@NotNull
	private Game game;

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

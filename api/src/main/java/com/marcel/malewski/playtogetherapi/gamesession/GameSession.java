package com.marcel.malewski.playtogetherapi.gamesession;

import com.marcel.malewski.playtogetherapi.game.Game;
import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.platform.Platform;
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
@Table(name = "gamesession")
public class GameSession {
	@Id
	@SequenceGenerator(name = "gamesession_sequence", sequenceName = "gamesession_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamesession_sequence")
	private Long id;
	@NotNull
	private String name;
	@Enumerated(EnumType.STRING)
	@NotNull
	private PrivacyLevel visibilityType;
	private boolean isCompetitive;
	@Enumerated(EnumType.STRING)
	@NotNull
	private PrivacyLevel accessType;
	@NotNull
	private LocalDate date;
	private int numberOfMembers;
	private int maxMembers;
	private int minAge;
	private boolean isCurrentUserMember;


	private String description;

	private String pendingMembers;//TODO to na potem
	private String availabilityTimes;
	@ManyToOne
	@JoinColumn(name = "gamer_id")
	@NotNull
	private Gamer creator;
	@ManyToOne
	@JoinColumn(name = "game_id")
	@NotNull
	private Game game;
	@ManyToMany
	@JoinTable(name = "gamesession_member",
					joinColumns = @JoinColumn(name = "gamesession_id"),
					inverseJoinColumns = @JoinColumn(name = "gamer_id"))
	@ToString.Exclude
	@NotNull
	private List<Gamer> members = new LinkedList<>();
	@ManyToMany
	@JoinTable(name = "gamesession_platform",
		joinColumns = @JoinColumn(name = "gamesession_id"),
		inverseJoinColumns = @JoinColumn(name = "platform_id"))
	@ToString.Exclude
	@NotNull
	private List<Platform> platforms = new LinkedList<>();

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

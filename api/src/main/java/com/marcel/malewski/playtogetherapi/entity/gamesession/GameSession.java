package com.marcel.malewski.playtogetherapi.entity.gamesession;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamesession.enums.PrivacyLevel;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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
	@NotNull
	private PrivacyLevel visibilityType;
	private boolean isCompetitive; //TODO jaki error poleci jak tu będzie null
	@NotNull
	private PrivacyLevel accessType;
	@NotNull
	private LocalDate date;
	@NotNull
	private LocalDate createdAt;
	@NotNull
	private LocalDate modifiedAt;
	@NotNull
	private Integer numberOfMembers;
	@NotNull //temp comment thank ti Integer + @NotNull there is notNull in database entity
	private Integer maxMembers;
	@NotNull
	private Integer minAge;

	private String description;//TODO co sie stanie jak bedzie przekazany jako null?
	private String availabilityTimes;//TODO to na potem, może jakaś paginacja

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
	@Builder.Default
	@NotNull
	private List<Gamer> members = new ArrayList<>();

	//TODO add pending members

	@ManyToMany
	@JoinTable(name = "gamesession_platform",
		joinColumns = @JoinColumn(name = "gamesession_id"),
		inverseJoinColumns = @JoinColumn(name = "platform_id"))
	@ToString.Exclude
	@Builder.Default
	@NotNull
	private List<Platform> platforms = new ArrayList<>();

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

	public static final String DATE_FIELD = "date";
	public static final String CREATED_AT_FIELD = "createdAt";
}

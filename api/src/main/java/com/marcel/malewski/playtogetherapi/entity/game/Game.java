package com.marcel.malewski.playtogetherapi.entity.game;

import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSession;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "game")
public class Game {
	@Id
	@SequenceGenerator(name = "game_sequence", sequenceName = "game_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_sequence")
	private int id;
	@NotNull
	@Column(unique = true)
	private String name;

	@OneToMany(mappedBy = "game")
	@ToString.Exclude
	@NotNull
	private Set<GameSession> gameSessions;

	@ManyToMany(mappedBy = "favouriteGames")
	@ToString.Exclude
	@NotNull
	private List<Gamer> gamers = new ArrayList<>();
}

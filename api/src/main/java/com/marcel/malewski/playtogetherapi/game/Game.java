package com.marcel.malewski.playtogetherapi.game;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.gamesession.GameSession;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
	private String name;
	@ManyToMany(mappedBy = "favouriteGames")
	@ToString.Exclude
	private Set<Gamer> gamers;
	@OneToMany(mappedBy = "game")
	@ToString.Exclude
	@NotNull
	private Set<GameSession> gameSessions;
}

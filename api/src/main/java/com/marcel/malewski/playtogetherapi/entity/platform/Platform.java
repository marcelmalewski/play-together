package com.marcel.malewski.playtogetherapi.entity.platform;

import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSession;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//TODO jak będzie dto do tworzenie platform to dać jakieś konkretne wymagania co do struktury nazwy np. wszystko uppercase albo cos
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity(name = "platform")
public class Platform {
	@Id
	@SequenceGenerator(name = "platform_sequence", sequenceName = "platform_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platform_sequence")
	private Long id;
	@Column(unique = true)
	@NotNull
	private String name;

	@ManyToMany(mappedBy = "platforms", fetch = FetchType.EAGER)
	@NotNull
	private List<Gamer> gamers = new ArrayList<>();

	@ManyToMany(mappedBy = "platforms", fetch = FetchType.EAGER)
	@NotNull
	private List<GameSession> gameSessions = new ArrayList<>();

	public Platform(@NotNull String name) {
		this.name = name;
	}

	public Platform(Long id, @NotNull String name) {
		this.id = id;
		this.name = name;
	}
}

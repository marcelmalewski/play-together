package com.marcel.malewski.playtogetherapi.platform;
//TODO moze ten enum nie jest potrzebny

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.gamesession.GameSession;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
	@Enumerated(EnumType.STRING)
	@NotNull
	private PlatformEnum name;

	@ManyToMany(mappedBy = "platforms")
	@NotNull
	private List<Gamer> gamers = new ArrayList<>();
	@ManyToMany(mappedBy = "platforms")
	@NotNull
	private List<GameSession> gameSessions = new ArrayList<>();
}

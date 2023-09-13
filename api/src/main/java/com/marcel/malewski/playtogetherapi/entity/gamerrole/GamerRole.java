package com.marcel.malewski.playtogetherapi.entity.gamerrole;

import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

//TODO na razie rola to name czyli zwykły string, ale chyba trzeba to rozbudować
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity(name = "gamerrole")
public class GamerRole {
	@Id
	@SequenceGenerator(name = "gamerrole_sequence", sequenceName = "gamerrole_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamerrole_sequence")
	private Long id;
	@Column(unique = true)
	@NotNull
	private String name;

	@ManyToMany(mappedBy = "roles")
	@NotNull
	private List<Gamer> gamers = new ArrayList<>();

	public GamerRole(@NotNull String name) {
		this.name = name;
	}

	public GamerRole(Long id, @NotNull String name) {
		this.id = id;
		this.name = name;
	}
}

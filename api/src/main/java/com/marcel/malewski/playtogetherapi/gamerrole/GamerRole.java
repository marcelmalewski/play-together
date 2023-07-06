package com.marcel.malewski.playtogetherapi.gamerrole;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
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
	private List<Gamer> gamers = new ArrayList<>();
}

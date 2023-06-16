package com.marcel.malewski.playtogetherapi.gamerrole;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class GamerRole {
	@Id
	@SequenceGenerator(name = "gamerrole_sequence", sequenceName = "gamerrole_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamerrole_sequence")
	private Long id;
	@Enumerated(EnumType.STRING)
	@NotNull
	private String name;
}

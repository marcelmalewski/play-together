package com.marcel.malewski.playtogetherapi.genre;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "genre")
public class Genre {
	@Id
	@SequenceGenerator(name = "genre_sequence", sequenceName = "genre_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_sequence")
	private int id;
	@NotNull
	private String name;
}

package com.marcel.malewski.playtogetherapi.entity.genre;

import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

	@ManyToMany(mappedBy = "favouriteGenres")
	@ToString.Exclude
	@NotNull
	private List<Gamer> gamer = new ArrayList<>();
}

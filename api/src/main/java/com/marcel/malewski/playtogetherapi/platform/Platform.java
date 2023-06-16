package com.marcel.malewski.playtogetherapi.platform;
//TODO moze ten enum nie jest potrzebny

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.gamerrole.GamerRoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedList;
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
	private GamerRoleEnum name;

	@ManyToMany(mappedBy = "platforms")
	private List<Gamer> gamers = new LinkedList<>();
}

package com.marcel.malewski.playtogetherapi.gamersgroup;

import com.marcel.malewski.playtogetherapi.gamer.Gamer;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "gamers_group")
public class GamersGroup {
	@Id
	@SequenceGenerator(name = "group_sequence", sequenceName = "group_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_sequence")
	private Long id;
	private String name;
	private String owner;
	private String description;
	private String visibilityType;
	private String accessType;
	private boolean isCompetitive;
	private int requiredAge;
	@ManyToMany
	@JoinTable(name = "gamer_gamers_group",
					joinColumns = @JoinColumn(name = "gamers_group_id"),
					inverseJoinColumns = @JoinColumn(name = "gamer_id"))
	@ToString.Exclude
	private Set<Gamer> gamers = new HashSet<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GamersGroup gamersGroup)) return false;

		return getId() != null ? getId().equals(gamersGroup.getId()) : gamersGroup.getId() == null;
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}

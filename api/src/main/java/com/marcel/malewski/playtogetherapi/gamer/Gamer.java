package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.gamesession.GameSession;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

//TODO moze użyć @Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "gamer")
//TODO dodać co moze byc nullem
public class Gamer implements UserDetails {
	@Id
	@SequenceGenerator(name = "gamer_sequence", sequenceName = "gamer_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamer_sequence")
	private Long id;
	private String login;
	private String password;
	private String email;
	private Date birthdate;
	private String bio;
	private String avatarUrl;
	private String playingTimeStart;
	private String playingTimeEnd;
	@Enumerated(EnumType.STRING)
	private Role role;
	@ManyToMany(mappedBy = "gamers")
	@ToString.Exclude
	private Set<GameSession> gameSessions = new HashSet<>();
	//   @Column(nullable = false)
	//   private String password;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Gamer gamer)) return false;

		return getId() != null ? getId().equals(gamer.getId()) : gamer.getId() == null;
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}

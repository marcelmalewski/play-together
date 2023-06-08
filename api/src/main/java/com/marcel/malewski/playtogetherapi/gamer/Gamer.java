package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.game.Game;
import com.marcel.malewski.playtogetherapi.gamesession.GameSession;
import com.marcel.malewski.playtogetherapi.genre.Genre;
import com.marcel.malewski.playtogetherapi.shared.Platform;
import com.marcel.malewski.playtogetherapi.shared.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

//
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "gamer")
public class Gamer implements UserDetails {
	@Id
	@SequenceGenerator(name = "gamer_sequence", sequenceName = "gamer_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamer_sequence")
	private Long id;
	@NotNull
	private String login;
	@NotNull
	private String password;
	@NotNull
	private String email;
	@NotNull
	private LocalDate birthdate;
	private String bio;
	private String avatarUrl;
	@NotNull
	private LocalTime playingTimeStart;
	@NotNull
	private LocalTime playingTimeEnd;
	@Enumerated(EnumType.STRING)
	@NotNull
	private Platform platforms;
	@Enumerated(EnumType.STRING)
	@ManyToMany
	@JoinTable(name = "gamer_favourite_game",
		joinColumns = @JoinColumn(name = "gamer_id"),
		inverseJoinColumns = @JoinColumn(name = "game_id"))
	@ToString.Exclude
	private List<Game> favouriteGames;
	@ManyToMany
	@JoinTable(name = "gamer_favourite_genre",
		joinColumns = @JoinColumn(name = "gamer_id"),
		inverseJoinColumns = @JoinColumn(name = "genre_id"))
	@ToString.Exclude
	private List<Genre> favouriteGenres;
	@NotNull
	private Role role;
	@NotNull
	private LocalDate createdAt;
	@ManyToMany(mappedBy = "members")
	@ToString.Exclude
	@NotNull
	private List<GameSession> joinedGameSessions = new LinkedList<>();
	@OneToMany(mappedBy = "creator")
	@ToString.Exclude
	@NotNull
	private List<GameSession> createdGameSessions = new LinkedList<>();

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

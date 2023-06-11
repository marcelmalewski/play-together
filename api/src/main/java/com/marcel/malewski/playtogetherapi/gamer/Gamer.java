package com.marcel.malewski.playtogetherapi.gamer;

import com.marcel.malewski.playtogetherapi.game.Game;
import com.marcel.malewski.playtogetherapi.gamesession.GameSession;
import com.marcel.malewski.playtogetherapi.genre.Genre;
import com.marcel.malewski.playtogetherapi.shared.GamerRole;
import com.marcel.malewski.playtogetherapi.shared.Platform;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

//TODO bio wymaga walidacji null a jak nie null to niepuste i to samo avatarUrl
//TODO upewnic sie ze tutaj jest tez pelna walidacja
//TODO napewno linked list ?
//TODO role zmienic na roles
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
	@Size(min = 3, max = 20)
	@NotNull
	private String login;
	@Size(min = 8, max = 20, message = "pass longer need to be")
	@NotNull
	private String password;
	@Email
	@NotNull
	private String email;
	@PastOrPresent
	@NotNull
	private LocalDate birthDate;
	private String bio;
	private String avatarUrl;
	@NotNull
	private LocalTime playingTimeStart;
	@NotNull
	private LocalTime playingTimeEnd;
	@UniqueElements
	@Size(min = 1)
	@NotNull
	private List<Platform> platforms;
	@ManyToMany
	@JoinTable(name = "gamer_favourite_game",
		joinColumns = @JoinColumn(name = "gamer_id"),
		inverseJoinColumns = @JoinColumn(name = "game_id"))
	@ToString.Exclude
	@NotNull
	private List<Game> favouriteGames = new LinkedList<>();
	@ManyToMany
	@JoinTable(name = "gamer_favourite_genre",
		joinColumns = @JoinColumn(name = "gamer_id"),
		inverseJoinColumns = @JoinColumn(name = "genre_id"))
	@ToString.Exclude
	@NotNull
	private List<Genre> favouriteGenres = new LinkedList<>();
	@Enumerated(EnumType.STRING)
	@NotNull
	private GamerRole role;
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
		return this.id.toString();
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

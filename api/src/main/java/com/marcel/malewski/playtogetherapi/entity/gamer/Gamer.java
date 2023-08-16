package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSession;
import com.marcel.malewski.playtogetherapi.entity.genre.Genre;
import com.marcel.malewski.playtogetherapi.entity.pendingmember.PendingMember;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//TODO bio wymaga walidacji null a jak nie null to niepuste i to samo avatarUrl
//TODO może dodać tutaj walidacje zbliżoną do registerDto?
//TODO co dokladnie znaczy joincolumn i inversejoin
//TODO co to dokładnie robi i czy jest to dobra opcja: @ManyToMany(fetch = FetchType.EAGER)
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
	@Column(unique = true)
	private String login;
	@NotNull
	private String password;
	@NotNull
	@Column(unique = true)
	private String email;
	@NotNull
	private LocalDate birthdate;
	@NotNull
	private LocalTime playingTimeStart;
	@NotNull
	private LocalTime playingTimeEnd;
	@NotNull
	private LocalDate createdAt;

	private String bio;
	private String avatarUrl;

	@OneToMany(mappedBy = "creator")
	@ToString.Exclude
	@Builder.Default
	@NotNull
	private List<GameSession> createdGameSessions = new ArrayList<>();

	//TODO add min one role
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "gamer_gamerrole",
		joinColumns = @JoinColumn(name = "gamer_id"),
		inverseJoinColumns = @JoinColumn(name = "gamerrole_id"))
	@ToString.Exclude
	@Builder.Default
	@NotNull
	private List<GamerRole> roles = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "gamer_platform",
		joinColumns = @JoinColumn(name = "gamer_id"),
		inverseJoinColumns = @JoinColumn(name = "platform_id"))
	@ToString.Exclude
	@Builder.Default
	@NotNull
	private List<Platform> platforms = new ArrayList<>();

	@ManyToMany(mappedBy = "members")
	@ToString.Exclude
	@Builder.Default
	@NotNull
	private List<GameSession> joinedGameSessions = new ArrayList<>();

	@OneToMany(mappedBy = "gamer")
	@Builder.Default
	@NotNull
	private List<PendingMember> pendingMembers = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "gamer_favourite_game",
		joinColumns = @JoinColumn(name = "gamer_id"),
		inverseJoinColumns = @JoinColumn(name = "game_id"))
	@ToString.Exclude
	@Builder.Default
	@NotNull
	private List<Game> favouriteGames = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "gamer_favourite_genre",
		joinColumns = @JoinColumn(name = "gamer_id"),
		inverseJoinColumns = @JoinColumn(name = "genre_id"))
	@ToString.Exclude
	@Builder.Default
	@NotNull
	private List<Genre> favouriteGenres = new ArrayList<>();

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
		return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
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

package com.marcel.malewski.playtogetherapi.entity.gamer;

import com.marcel.malewski.playtogetherapi.entity.game.Game;
import com.marcel.malewski.playtogetherapi.entity.gamerprivilege.GamerPrivilege;
import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSession;
import com.marcel.malewski.playtogetherapi.entity.genre.Genre;
import com.marcel.malewski.playtogetherapi.entity.pendingmember.PendingMember;
import com.marcel.malewski.playtogetherapi.entity.platform.Platform;
import com.marcel.malewski.playtogetherapi.validation.notblankifexist.TrimmedSize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerValidationConstants.LOGIN_MAX_SIZE;
import static com.marcel.malewski.playtogetherapi.entity.gamer.GamerValidationConstants.LOGIN_MIN_SIZE;
import static com.marcel.malewski.playtogetherapi.validation.ValidationConstants.*;

//TODO remove this Eager
//TODO add better validation to password
//TODO add validation to dates
//TODO add better validation to avatarUrl
//TODO it is possible to validate password to check if it is encrypted password?
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
    @Version
    private Integer version;

    @Size(min = LOGIN_MIN_SIZE, max = LOGIN_MAX_SIZE)
    @NotBlank
    @Column(unique = true)
    private String login;
    @NotBlank
    private String password;
    @Email
    @NotNull
    @Column(unique = true)
    private String email;
    @NotNull
    private LocalDate birthdate;
    @NotNull
    private LocalTime playingTimeStart;
    @NotNull
    private LocalTime playingTimeEnd;
    @CreationTimestamp
    private LocalDate createdAt;
    @TrimmedSize(min = 3, max = 500)
    private String bio;
    @TrimmedSize
    private String avatarUrl;

    @Size(min = 1, message = AT_LEAST_ONE_ROLE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gamer_gamerrole", joinColumns = @JoinColumn(name = "gamer_id"), inverseJoinColumns = @JoinColumn(name = "gamerrole_id"))
    @ToString.Exclude
    @Builder.Default
    @NotNull
    private List<GamerRole> roles = new ArrayList<>();

    @Size(min = 1, message = AT_LEAST_ONE_PLATFORM_ID)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gamer_platform", joinColumns = @JoinColumn(name = "gamer_id"), inverseJoinColumns = @JoinColumn(name = "platform_id"))
    @ToString.Exclude
    @Builder.Default
    @NotNull
    private List<Platform> platforms = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    @ToString.Exclude
    @Builder.Default
    @NotNull
    private List<GameSession> createdGameSessions = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    @ToString.Exclude
    @Builder.Default
    @NotNull
    private List<GameSession> joinedGameSessions = new ArrayList<>();

    @OneToMany(mappedBy = "gamer", fetch = FetchType.EAGER)
    @Builder.Default
    @NotNull
    private List<PendingMember> pendingMembers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "gamer_favourite_game",
            joinColumns = @JoinColumn(name = "gamer_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    @ToString.Exclude
    @Builder.Default
    @NotNull
    private List<Game> favouriteGames = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
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
        List<GamerPrivilege> privileges = extractPrivilegesFromRoles(this.roles);

        return privileges.stream().map(privilege -> new SimpleGrantedAuthority(privilege.getName())).toList();
    }

    private List<GamerPrivilege> extractPrivilegesFromRoles(List<GamerRole> roles) {
        List<GamerPrivilege> extractedPrivileges = new ArrayList<>();
        roles.forEach(role -> extractedPrivileges.addAll(role.getGamerPrivileges()));

        return extractedPrivileges;
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

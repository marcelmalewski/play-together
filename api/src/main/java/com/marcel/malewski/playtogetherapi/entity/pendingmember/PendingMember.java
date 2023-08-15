package com.marcel.malewski.playtogetherapi.entity.pendingmember;

import com.marcel.malewski.playtogetherapi.entity.gamer.Gamer;
import com.marcel.malewski.playtogetherapi.entity.gamesession.GameSession;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class PendingMember {
	@EmbeddedId
	private PendingMemberKey id;

	@ManyToOne
	@MapsId("gameSessionId")
	@JoinColumn(name = "gamesession_id")
	private GameSession gameSession;

	@ManyToOne
	@MapsId("gamerId")
	@JoinColumn(name = "gamer_id")
	private Gamer gamer;

	@NotNull
	private LocalDate requestedAt;
	private String message;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PendingMember that)) return false;

		return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}

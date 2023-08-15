package com.marcel.malewski.playtogetherapi.entity.pendingmember;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class PendingMemberKey implements Serializable {
	@Column(name = "gamesession_id")
	private Long gameSessionId;

	@Column(name = "gamer_id")
	private Long gamerId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PendingMemberKey that)) return false;

		if (!gameSessionId.equals(that.gameSessionId)) return false;
		return gamerId.equals(that.gamerId);
	}

	@Override
	public int hashCode() {
		int result = gameSessionId.hashCode();
		result = 31 * result + gamerId.hashCode();
		return result;
	}
}

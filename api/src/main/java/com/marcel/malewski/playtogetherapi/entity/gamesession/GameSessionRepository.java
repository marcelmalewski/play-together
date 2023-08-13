package com.marcel.malewski.playtogetherapi.entity.gamesession;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
}

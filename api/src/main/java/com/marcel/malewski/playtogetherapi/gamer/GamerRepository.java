package com.marcel.malewski.playtogetherapi.gamer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GamerRepository extends JpaRepository<Gamer, Long> {
	Optional<Gamer> findByLogin(String login);
}

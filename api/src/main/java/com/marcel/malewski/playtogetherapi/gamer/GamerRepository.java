package com.marcel.malewski.playtogetherapi.gamer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GamerRepository extends JpaRepository<Gamer, Long> {
	Optional<Gamer> findByLogin(String login);
}

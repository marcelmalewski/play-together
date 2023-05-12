package com.marcel.malewski.playtogetherapi.gamer;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GamerRepository extends CrudRepository<Gamer, Long> {
	Optional<Gamer> findByLogin(String login);
}

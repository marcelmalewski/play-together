package com.marcel.malewski.playtogetherapi.entity.gamer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GamerRepository extends JpaRepository<Gamer, Long> {
	List<Gamer> findAllByLoginIsLikeIgnoreCase(String gamerLogin);
	Optional<Gamer> findByLoginOrEmail(String login, String email);
	boolean existsByLogin(String login);
	boolean existsByEmail(String email);
}


package com.marcel.malewski.playtogetherapi.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	boolean existsByLogin(String login);
	Person findByLogin(String login);
}

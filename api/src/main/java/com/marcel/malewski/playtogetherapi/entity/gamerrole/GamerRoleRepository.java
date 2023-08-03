package com.marcel.malewski.playtogetherapi.entity.gamerrole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GamerRoleRepository extends JpaRepository<GamerRole, Long> {
	Optional<GamerRole> findByName(String name);
	GamerRole getReferenceByName(String name);
	boolean existsByName(String name);
}

package com.marcel.malewski.playtogetherapi.gamerrole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamerRoleRepository extends JpaRepository<GamerRole, Long> {
}

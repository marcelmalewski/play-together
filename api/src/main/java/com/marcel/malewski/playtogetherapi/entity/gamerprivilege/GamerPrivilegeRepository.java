package com.marcel.malewski.playtogetherapi.entity.gamerprivilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamerPrivilegeRepository extends JpaRepository<GamerPrivilege, Long> {
}

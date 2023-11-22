package com.marcel.malewski.playtogetherapi.entity.gamerprivilege;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity(name = "gamerprivilege")
public class GamerPrivilege {
    @Id
    @SequenceGenerator(name = "gamerprivilege_sequence", sequenceName = "gamerprivilege_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gamerprivilege_sequence")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "gamerPrivileges")
    private Collection<GamerRole> gamerRoles;
}

package com.marcel.malewski.playtogetherapi.entity.gamerprivilege;

import com.marcel.malewski.playtogetherapi.entity.gamerrole.GamerRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    @ToString.Exclude
    @NotNull
    private List<GamerRole> gamerRoles = new ArrayList<>();

    public GamerPrivilege(@NotNull String name) {
        this.name = name;
    }
}

package com.attijarivos.DTO;

import com.attijarivos.model.Siege;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TeamResponse {
    private String idTeam;
    private String nomTeam;
    private String descriptionTeam;
    private Siege siege;
    private Set<MembreDTO> membres = new HashSet<>();
}

package com.attijarivos.dto.team;

import com.attijarivos.dto.membre.MembreResponse;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DetailTeamResponse {
    private String idTeam;
    private String nomTeam;
    private String descriptionTeam;
    private Siege siege;
    private Set<MembreResponse> membres = new HashSet<>();

}

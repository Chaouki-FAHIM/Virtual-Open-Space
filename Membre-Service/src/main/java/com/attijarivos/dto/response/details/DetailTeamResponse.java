package com.attijarivos.dto.response.details;

import com.attijarivos.dto.response.shorts.ShortMembreResponse;
import com.attijarivos.dto.Siege;
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
    private Set<ShortMembreResponse> membres = new HashSet<>();

}

package com.attijarivos.dto.team;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ShortTeamResponse {
    private String idTeam;
    private String nomTeam;
    private Siege siege;
}

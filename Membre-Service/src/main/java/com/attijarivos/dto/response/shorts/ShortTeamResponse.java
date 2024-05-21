package com.attijarivos.dto.response.shorts;

import com.attijarivos.dto.Siege;
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

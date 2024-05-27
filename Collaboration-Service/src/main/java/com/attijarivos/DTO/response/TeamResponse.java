package com.attijarivos.DTO.response;

import com.attijarivos.DTO.Siege;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TeamResponse {
    private String idTeam;
    private String nomTeam;
    private Siege siege;
}

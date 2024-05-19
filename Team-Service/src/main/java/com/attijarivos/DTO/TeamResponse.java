package com.attijarivos.DTO;

import com.attijarivos.model.Siege;
import lombok.*;

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
}

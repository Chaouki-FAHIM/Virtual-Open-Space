package com.attijarivos.DTO;

import com.attijarivos.model.Siege;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

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
    private List<MembreDTO> membres;
}

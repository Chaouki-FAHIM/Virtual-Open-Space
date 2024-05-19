package com.attijarivos.model;

import com.attijarivos.DTO.MembreDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Document("team")
public class Team {
    @Id
    private String idTeam;
    private String nomTeam;
    private String descriptionTeam;
    private Siege siege;
    private Set<MembreDTO> membres;
}

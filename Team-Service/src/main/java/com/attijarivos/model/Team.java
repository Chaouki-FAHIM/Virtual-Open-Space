package com.attijarivos.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}

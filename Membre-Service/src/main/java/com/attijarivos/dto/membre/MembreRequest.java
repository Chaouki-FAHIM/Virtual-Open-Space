package com.attijarivos.dto.membre;

import com.attijarivos.model.RoleHabilation;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MembreRequest {
    private String nomMembre;
    private String prenom;
    private RoleHabilation roleHabilation;
    private String idTeam;
}

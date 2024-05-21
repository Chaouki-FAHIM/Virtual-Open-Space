package com.attijarivos.dto.request;

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

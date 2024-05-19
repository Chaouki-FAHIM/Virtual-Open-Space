package com.attijarivos.dto;

import com.attijarivos.model.RoleHabilation;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MembreRequest {
    private String nom;
    private String prenom;
    private RoleHabilation roleHabilation;
    private String idTeam;
}

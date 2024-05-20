package com.attijarivos.dto;

import com.attijarivos.model.RoleHabilation;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MembreAndOurTeamResponse {
    private String idMembre;
    private String nomMembre;
    private String prenom;
    private RoleHabilation roleHabilation;
    private boolean statutCollaboration;
    private Set<TeamResponse> teams=  new HashSet<>();
}

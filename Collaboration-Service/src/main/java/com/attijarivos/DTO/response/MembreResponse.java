package com.attijarivos.DTO.response;

import com.attijarivos.DTO.RoleHabilation;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MembreResponse {
    private String idMembre;
    private String nomMembre;
    private String prenom;
    private RoleHabilation roleHabilation;
    private boolean statutCollaboration;
    private Set<TeamResponse> teams= new HashSet<>();
}

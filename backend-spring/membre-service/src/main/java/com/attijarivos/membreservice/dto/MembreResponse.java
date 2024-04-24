package com.attijarivos.membreservice.dto;

import com.attijarivos.membreservice.model.RoleHabilation;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MembreResponse {
    private String id;
    private String nom;
    private String prenom;
    private RoleHabilation roleHabilation;
    private boolean statutCollaboration;

}

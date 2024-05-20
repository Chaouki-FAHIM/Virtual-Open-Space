package com.attijarivos.dto;

import com.attijarivos.model.RoleHabilation;
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

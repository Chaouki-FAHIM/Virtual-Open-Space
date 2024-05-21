package com.attijarivos.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MembreDTO {
    private String idMembre;
    private String nomMembre;
    private String prenom;
    private RoleHabilation roleHabilation;
    private boolean statutCollaboration;
}

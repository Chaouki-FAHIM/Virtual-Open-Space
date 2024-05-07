package com.attijarivos.DTO;

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
    private enum roleHabilation {
        IOS,ANDROID,BACKEND,FRONTEND,TEST,DEVOPS,DESIGN
    }
    private boolean statutCollaboration;
}

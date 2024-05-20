package com.attijarivos.DTO.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MembreResponse {
    private String idMembre;
    private String nomMembre;
    private String prenom;
    private enum roleHabilation {
        IOS,ANDROID,BACKEND,FRONTEND,TEST,DEVOPS,DESIGN
    }
    private boolean statutCollaboration;
}

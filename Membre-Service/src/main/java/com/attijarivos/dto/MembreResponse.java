package com.attijarivos.dto;

import com.attijarivos.model.RoleHabilation;
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
    private RoleHabilation roleHabilation;
    private boolean statutCollaboration;

    @Override
    public String toString() {
        return "MembreResponse{" +
                "idMembre='" + idMembre + '\'' +
                ", nomMembre='" + nomMembre + '\'' +
                ", prenom='" + prenom + '\'' +
                ", roleHabilation=" + roleHabilation +
                ", statutCollaboration=" + statutCollaboration +
                '}';
    }
}

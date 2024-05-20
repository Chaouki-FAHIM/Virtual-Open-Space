package com.attijarivos.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Document("membre")
public class Membre {
    @Id
    private String idMembre;
    private String nomMembre;
    private String prenom;
    private RoleHabilation roleHabilation;
    private boolean statutCollaboration;
}

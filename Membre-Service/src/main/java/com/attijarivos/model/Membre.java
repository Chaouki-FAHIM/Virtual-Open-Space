package com.attijarivos.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Document("membre")
public class Membre {
    @Id
    private String id;
    private String nom;
    private String prenom;
    private RoleHabilation roleHabilation;
    private boolean statutCollaboration;
    @DBRef
    private Set<String> teams = new HashSet<>();
}

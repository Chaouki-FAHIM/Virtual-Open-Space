package com.attijarivos.DTO.response;

import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CollaborationResponse {
    private Long idCollaboration;
    private String titre;
    private Date dateCreationCollaboration;
    private Date dateDepart;
    private Boolean confidentielle;
    private String idProprietaire;
    private Set<MembreResponse> participants = new HashSet<>();

    public String toString() {
        return "{idCollaboration:" + idCollaboration +
                ",titre:"+titre+
                ",dateCreationCollaboration:"+dateCreationCollaboration +
                ",dateDepart:"+dateDepart+
                ",confidentielle:"+confidentielle+
                ",idProprietaire:"+idProprietaire+
                "}";
    }
}

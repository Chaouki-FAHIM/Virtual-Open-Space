package com.attijarivos.DTO.response;

import lombok.*;

import java.util.Date;

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

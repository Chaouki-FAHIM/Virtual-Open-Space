package com.attijarivos.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CollaborationResponse {
    private Long idCollaboration;
    private String titre;
    private Date dateCreationCollaboration;
    private Date dateDepart;
    private Boolean confidentielle;
    private String idProprietaire;
    private Boolean visible;
}

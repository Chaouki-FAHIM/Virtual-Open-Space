package com.attijarivos.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class CollaborationRequest {
    private String titre;
    private Date dateDepart;
    private Boolean confidentielle;
    private String idProprietaire;
}

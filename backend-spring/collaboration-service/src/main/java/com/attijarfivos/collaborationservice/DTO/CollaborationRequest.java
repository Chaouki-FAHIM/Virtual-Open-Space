package com.attijarfivos.collaborationservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class CollaborationRequest {
    private String titre;
    private Date dateDepart;
    private Boolean confidentielle;
    private Long proprietaire;
    private Long invites;
}

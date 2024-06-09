package com.attijarivos.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class CollaborationCreateRequest {
    private String titre;
    private Date dateDepart;
    private Boolean confidentielle;
    private String idProprietaire;
    private Set<String> idInvites = new HashSet<>();
}

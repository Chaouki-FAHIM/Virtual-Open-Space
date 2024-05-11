package com.attijarivos.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class CollaborationUpdateRequest {
    private String titre;
    private Date dateDepart;
    private Boolean confidentielle;
}

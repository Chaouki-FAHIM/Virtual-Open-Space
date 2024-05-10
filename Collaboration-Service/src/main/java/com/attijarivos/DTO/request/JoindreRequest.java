package com.attijarivos.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JoindreRequest {
    private String idMembre;
    private Long idCollaboration;
    private Date dateParticiaption;
}

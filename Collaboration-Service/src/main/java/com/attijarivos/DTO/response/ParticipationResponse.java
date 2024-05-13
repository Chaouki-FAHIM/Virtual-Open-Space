package com.attijarivos.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ParticipationResponse {
    private Long idParticipation;
    private String idParticipant;
    private CollaborationResponse collaboration;
    private Date dateParticiaption;
}


package com.attijarivos.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ParticipationRequest {
    private String idParticipant;
    private Long idCollaboration;
}

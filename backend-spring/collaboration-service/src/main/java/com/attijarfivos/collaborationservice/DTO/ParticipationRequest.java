package com.attijarfivos.collaborationservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ParticipationRequest {
    private Long membre;
    private Long collaboration;
    private Date date;
}

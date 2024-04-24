package com.attijarfivos.collaborationservice.DTO;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ParticipationResponse {
    private Long id;
    private Long membre;
    private Long collaboration;
    private Date date;
}

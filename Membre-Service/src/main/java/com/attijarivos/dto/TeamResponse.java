package com.attijarivos.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TeamResponse {
    private String idTeam;
    private String nomTeam;
    private String descriptionTeam;
    private enum siege { YACOUB_EL_MANSOUR, HASSAN_2, ROUDANI, MOULAY_YOUSSEF};
    private List<MembreResponse> membres;
}

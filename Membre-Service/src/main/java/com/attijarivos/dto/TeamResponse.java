package com.attijarivos.dto;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<MembreResponse> membres = new HashSet<>();
}

package com.attijarivos.DTO;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TeamMembresRequest {
    private List<String> idMembres;
}

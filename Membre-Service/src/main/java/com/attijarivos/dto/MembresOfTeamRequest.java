package com.attijarivos.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MembresOfTeamRequest {
    private List<String> idMembres;
}

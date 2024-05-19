package com.attijarivos.mapper;

import com.attijarivos.DTO.TeamRequest;
import com.attijarivos.DTO.TeamResponse;
import com.attijarivos.model.Team;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public Team fromReqToTeam(TeamRequest teamRequest) {

        return Team.builder()
                .nomTeam(teamRequest.getNomTeam())
                .descriptionTeam(teamRequest.getDescriptionTeam())
                .siege(teamRequest.getSiege())
                .build();
    }

    public TeamResponse fromTeamToRes(Team team) {
        TeamResponse teamResponse = new TeamResponse();
        BeanUtils.copyProperties(team, teamResponse);
        return teamResponse;
    }
}

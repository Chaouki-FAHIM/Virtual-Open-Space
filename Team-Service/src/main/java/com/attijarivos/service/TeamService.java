package com.attijarivos.service;

import com.attijarivos.DTO.TeamRequest;
import com.attijarivos.DTO.TeamResponse;
import com.attijarivos.mapper.TeamMapper;
import com.attijarivos.model.Team;
import com.attijarivos.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    @Autowired
    private final TeamRepository teamRepository;
    @Autowired
    private final TeamMapper teamMapper;

    public TeamResponse createTeam(TeamRequest teamRequest) {
        return null;
    }

    public List<TeamResponse> getAllTeams() {
        return List.of(null);
    }
    public TeamResponse getTeamById(String idTeam) {
        return null;
    }
    public List<TeamResponse> getTeamByName(String name) {
        return null;
    }

}

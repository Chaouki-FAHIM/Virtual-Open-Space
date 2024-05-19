package com.attijarivos.service;

import com.attijarivos.DTO.MembreDTO;
import com.attijarivos.DTO.TeamMembresRequest;
import com.attijarivos.DTO.TeamRequest;
import com.attijarivos.DTO.TeamResponse;
import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RededicationMembreException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.mapper.TeamMapper;
import com.attijarivos.model.Team;
import com.attijarivos.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    @Qualifier("webClient-layer-config")
    private final WebClient webClient;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;


    private Boolean isNotNullValue(Object value) {
        return value == null || Objects.equals(value, "");
    }

    private void verifyDataTeam(TeamRequest teamRequest) throws RequiredDataException {
        if(isNotNullValue(teamRequest.getNomTeam())) {
            log.info("Nom de team : {}", teamRequest.getNomTeam());
            throw new RequiredDataException("Nom de team", "l'ajout","d'équipe");
        }

        if(isNotNullValue(teamRequest.getSiege())) {
            log.info("Siège de team : {}", teamRequest.getSiege());
            throw new RequiredDataException("Siège de team", "l'ajout","d'équipe");
        }
    }


    public TeamResponse createTeam(TeamRequest teamRequest) throws RequiredDataException {
        verifyDataTeam(teamRequest);

        Team team = teamMapper.fromReqToTeam(teamRequest);

        log.info("Equipe est enregistrée : {}", team);

        return teamMapper.fromTeamToRes(
                teamRepository.save(team)
        );
    }

    public List<TeamResponse> getAllTeams() {
        return List.of(null);
    }

    public TeamResponse getTeamById(String idTeam) throws NotFoundDataException {
        Optional<Team> team = Optional.of(receiveTeam(idTeam));

        return teamMapper.fromTeamToRes(team.get());
    }

    public List<TeamResponse> getTeamByName(String name) {
        return null;
    }

    private Team receiveTeam(String idTeam) throws NotFoundDataException {
        Optional<Team> team = teamRepository.findByIdTeam(idTeam);

        if(team.isEmpty()) throw new NotFoundDataException(idTeam);
        return team.get();
    }

    private Optional<MembreDTO> receiveMembreById(String idMembre) throws MicroserviceAccessFailureException {

        try {
            return Optional.ofNullable(
                    webClient.get().uri(WebClientConfig.MEMBRE_SERVICE_URL + "/"+ idMembre).retrieve().bodyToMono(MembreDTO.class).block()
            );
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Membre-Service", e);
            throw new MicroserviceAccessFailureException("Membre");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    private int numbreOfEquipeProcessedForAddList = 0;

    @Transactional
    public TeamResponse addMembresToTeam(String idTeam, TeamMembresRequest teamMembresRequest) throws RequiredDataException, NotFoundDataException, MicroserviceAccessFailureException, RededicationMembreException {

        Optional<Team> team = Optional.of(receiveTeam(idTeam));

        List<String> membreDTOListRequest = teamMembresRequest.getIdMembres();
        Set<MembreDTO> membreDTOSet = team.get().getMembres();

//        membreDTOListRequest.stream().filter(
//                membreDTO -> {
//                    numbreOfEquipeProcessedForAddList++;
//                    if (membreDTOListRequest.contains(membreDTO)) {
//                        try {
//                            throw new RededicationMembreException(numbreOfEquipeProcessedForAddList);
//                        } catch (RededicationMembreException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                    if(receiveMembreById(membreDTO))
//                        throw new NotFoundDataException("Identifant de membre",membreDTO);
//                    else membreDTOSet.add();
//                }
//
//        );

        team.get().setMembres(membreDTOSet);

        return teamMapper.fromTeamToRes(
                teamRepository.save(team.get())
        );
    }

}

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

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    @Qualifier("webClient-layer-config")
    private final WebClient.Builder webClientBuilder;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private int numbreOfEquipeProcessedForAddList;


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
        List<Team> teams = teamRepository.findAll();
        log.info("Teams tourvées sont : {}",teams);

        return teams.stream().map(teamMapper::fromTeamToRes).toList();
    }

    public TeamResponse getTeamById(String idTeam) throws NotFoundDataException {
        Optional<Team> team = Optional.of(receiveTeam(idTeam));

        return teamMapper.fromTeamToRes(team.get());
    }

    private Team receiveTeam(String idTeam) throws NotFoundDataException {
        Optional<Team> team = teamRepository.findByIdTeam(idTeam);

        if(team.isEmpty()) throw new NotFoundDataException(idTeam);
        return team.get();
    }

    private Optional<MembreDTO> receiveMembreById(String idMembre) throws Exception {

        try {
            return Optional.ofNullable(
                    webClientBuilder.build().get().uri(WebClientConfig.MEMBRE_SERVICE_URL + "/"+ idMembre).retrieve().bodyToMono(MembreDTO.class).block()
            );
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Membre-Service", e);
            throw new MicroserviceAccessFailureException("Membre");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public TeamResponse addMembresToTeam(String idTeam, TeamMembresRequest teamMembresRequest) throws Exception {

        Optional<Team> team = Optional.of(receiveTeam(idTeam));

        List<String> membreDTOListRequest = teamMembresRequest.getIdMembres();
        Set<MembreDTO> membresOfTeam = team.get().getMembres();

        numbreOfEquipeProcessedForAddList = 0;

        for (String idMembre : membreDTOListRequest) {
            Optional<MembreDTO> membreDTO = receiveMembreById(idMembre);

            if(team.get().getMembres() != null && team.get().getMembres().contains(membreDTO.get())) {
                log.warn("Le membre numero "+numbreOfEquipeProcessedForAddList+ " dans votre liste est déjà exister dans cette équipe");
                throw new RededicationMembreException(numbreOfEquipeProcessedForAddList);
            }
            else {
                log.info("Membre avec l'identifiant {} est été ajouter dans le team  : {}", membreDTO.get(), idTeam);
                membresOfTeam.add(membreDTO.get());
            }

            numbreOfEquipeProcessedForAddList++;
        }

        team.get().setMembres(membresOfTeam);

        return teamMapper.fromTeamToRes(
                teamRepository.save(team.get())
        );
    }

    public boolean deleteTeam(String idTeam) throws NotFoundDataException {
        Optional<Team> team = Optional.of(receiveTeam(idTeam));
        log.info("Team d'id est bien supprimée : {}",idTeam);
        teamRepository.delete(team.get());
        return true;
    }

    @Transactional
    public Set<TeamResponse> syncUpdateMemberInTeam(MembreDTO membreDTO) {

        Set<TeamResponse> teamsOfMembre = new HashSet<>();
        for(Team team : teamRepository.findAll())
            for(MembreDTO membre : team.getMembres())
                if(membre.getIdMembre().equals(membreDTO.getIdMembre())) {
                    teamsOfMembre.add(teamMapper.fromTeamToRes(team));
                    membre.setNomMembre(membreDTO.getNomMembre());
                    membre.setPrenom(membreDTO.getPrenom());
                    membre.setRoleHabilation(membreDTO.getRoleHabilation());
                    teamRepository.save(team);
                    break;
                }


        return teamsOfMembre;
    }

    public Set<TeamResponse> syncDeleteMemberInTeam(String idMembre) {
        Set<TeamResponse> teamsOfMembre = new HashSet<>();
        for(Team team : teamRepository.findAll()) {
            for(MembreDTO membre : team.getMembres())
                if(membre.getIdMembre().equals(idMembre)) {
                    teamsOfMembre.add(teamMapper.fromTeamToRes(team));
                    team.getMembres().remove(membre);
                    break;
                }
            teamRepository.save(team);
        }
        return Set.of();
    }
}

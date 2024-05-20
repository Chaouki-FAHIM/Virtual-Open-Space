package com.attijarivos.service;

import com.attijarivos.dto.MembreAndOurTeamResponse;
import com.attijarivos.dto.MembreResponse;
import com.attijarivos.dto.TeamResponse;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.dto.MembreRequest;
import com.attijarivos.mapper.MembreMapper;
import com.attijarivos.model.Membre;
import com.attijarivos.repository.MembreRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service

@Slf4j
public class MembreService extends WebClientOperations {

    private final MembreRespository membreRespository;
    private final MembreMapper membreMapper;

    public MembreService(WebClient webClient, MembreRespository membreRespository, MembreMapper membreMapper) {
        super(webClient);
        this.membreRespository = membreRespository;
        this.membreMapper = membreMapper;
    }


    private Boolean isNotNullValue(Object value) {
        return value == null || Objects.equals(value, "");
    }

    public MembreResponse createMembre(MembreRequest membreRequest) throws MicroserviceAccessFailureException, RequiredDataException {

        if(isNotNullValue(membreRequest.getNomMembre())) {
            throw new RequiredDataException("Nom est obligatoire pour l'ajout d'un nouveau membre");
        }
        if(isNotNullValue(membreRequest.getPrenom())) {
            throw new RequiredDataException("Prénom est obligatoire pour l'ajout d'un nouveau membre");
        }
        if(membreRequest.getRoleHabilation()== null ) {
            throw new RequiredDataException("Rôle d'habilation est obligatoire pour l'ajout d'un nouveau membre");
        }
        if(isNotNullValue(membreRequest.getIdTeam())) {
            throw new RequiredDataException("Identifiant d'équipe est obligatoire pour l'ajout d'un nouveau membre");
        }

        verifyTeamById(membreRequest.getIdTeam());

        Membre membre = membreRespository.save(
                membreMapper.fromReqToMembre(membreRequest)
        );

        if (addMembreToTeam(membre.getIdMembre(),membreRequest.getIdTeam()) )
            log.info("Bonne ajout d'un nouveau membre à nouvelle équipe !!");
        else log.warn("Mauvaise ajout d'un nouveau membre à nouvelle équipe !!");

        log.info("Membre d'id {} est enregistré!!",membre.getIdMembre());

        return membreMapper.fromMembreToRes(membre);
    }

    public List<MembreResponse> getAllMembres() {
        List<Membre> membres = membreRespository.findAll();
        log.info("Membres tourvés sont : {}",membres.toString());
        return membres.stream().map(membreMapper::fromMembreToRes).toList();
    }

    public MembreAndOurTeamResponse getOneMembreDetail(String idMembre) throws NotFoundDataException, MicroserviceAccessFailureException {
        Optional<Membre> membre = membreRespository.findById(idMembre);
        log.info("Membre trouvé est : {}", membre);
        if (membre.isEmpty()) throw new NotFoundDataException(idMembre);

        List<TeamResponse> teamResponseList = receiveAllTeams();

        MembreAndOurTeamResponse response = MembreAndOurTeamResponse.builder()
                .idMembre(membre.get().getIdMembre())
                .nomMembre(membre.get().getNomMembre())
                .prenom(membre.get().getPrenom())
                .roleHabilation(membre.get().getRoleHabilation())
                .statutCollaboration(membre.get().isStatutCollaboration())
                .build();

        Set<TeamResponse> teamResponseSet = new HashSet<>();

        for (TeamResponse teamResponse : teamResponseList) {
            if (teamResponse.getMembres() != null) {
                for (MembreResponse membreResponse : teamResponse.getMembres()) {
                    if (membreResponse.getIdMembre() != null && membreResponse.getIdMembre().equals(idMembre)) {
                        teamResponseSet.add(teamResponse);
                        break;  // Exit the loop once a match is found
                    }
                }
            }
        }

        response.setTeams(teamResponseSet);

        return response;
    }

}

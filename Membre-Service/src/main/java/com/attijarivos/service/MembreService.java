package com.attijarivos.service;

import com.attijarivos.dto.MembreResponse;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.dto.MembreRequest;
import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.mapper.MembreMapper;
import com.attijarivos.model.Membre;
import com.attijarivos.repository.MembreRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MembreService {

    @Qualifier("webClient-layer-config")
    private final WebClient webClient;
    private final MembreRespository membreRespository;
    private final MembreMapper membreMapper;


    private Boolean isNotNullValue(Object value) {
        return value == null || Objects.equals(value, "");
    }

    private boolean verifyTeamById(String idTeam) throws MicroserviceAccessFailureException {

        try {
            return webClient.get().uri(WebClientConfig.TEAM_SERVICE_URL + "/" + idTeam).retrieve().bodyToMono(Object.class).block() != null;
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Team-Service", e);
            throw new MicroserviceAccessFailureException("Team");
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private void addMembreToTeam(String idTeam, String idMembre) throws MicroserviceAccessFailureException {

        try {
            webClient.patch()
                    .uri(WebClientConfig.TEAM_SERVICE_URL + "/" + idTeam + "/membres")
                    .bodyValue(List.of(idMembre))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Team-Service", e);
            throw new MicroserviceAccessFailureException("Team");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    public MembreResponse createMembre(MembreRequest membreRequest) throws Exception {

        if(isNotNullValue(membreRequest.getNom())) {
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

        if (verifyTeamById(membreRequest.getIdTeam()))
        {
            addMembreToTeam(membreRequest.getIdTeam(), membreRequest.getIdTeam());
        }

        Membre membre = membreMapper.fromReqToMembre(membreRequest);
        membreRespository.save(membre);

        log.info("Membre d'id {} est enregistré!!",membre.getId());

        return membreMapper.fromMembreToRes(membre);
    }

    public List<MembreResponse> getAllMembres() {
        List<Membre> membres = membreRespository.findAll();
        log.info("Membres tourvés sont : {}",membres.toString());
        return membres.stream().map(membreMapper::fromMembreToRes).toList();
    }

    public MembreResponse getMembre(String id) throws NotFoundDataException {
        Optional<Membre> membre = membreRespository.findById(id);
        log.info("Membre tourvé est : {}",membre);
        if(membre.isEmpty()) throw new NotFoundDataException(id);
        return membre.map(membreMapper::fromMembreToRes).orElse(null);
    }
}

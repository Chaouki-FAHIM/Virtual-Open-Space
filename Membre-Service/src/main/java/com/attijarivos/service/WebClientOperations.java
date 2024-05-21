package com.attijarivos.service;

import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.dto.MembresOfTeamRequest;
import com.attijarivos.dto.team.DetailTeamResponse;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class WebClientOperations {

    @Qualifier("webClient-layer-config")
    private final WebClient webClient;

    protected void verifyTeamById(String idTeam) throws MicroserviceAccessFailureException {

        try {
            webClient.get().uri(WebClientConfig.TEAM_SERVICE_URL + "/" + idTeam).retrieve().bodyToMono(Object.class).block() ;
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Team-Service", e);
            throw new MicroserviceAccessFailureException("Team");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    protected boolean addMembreToTeam(String idMembre, String idTeam) throws MicroserviceAccessFailureException {
        try {
            MembresOfTeamRequest membresOfTeamRequest = new MembresOfTeamRequest();
            membresOfTeamRequest.setIdMembres(List.of(idMembre));

            Optional<DetailTeamResponse> teamResponse = Optional.ofNullable(webClient.patch()
                    .uri(WebClientConfig.TEAM_SERVICE_URL + "/" + idTeam + "/membres")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(membresOfTeamRequest)
                    .retrieve()
                    .bodyToMono(DetailTeamResponse.class)
                    .block());
            return teamResponse.isPresent();
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Team-Service", e);
            throw new MicroserviceAccessFailureException("Team");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    protected List<DetailTeamResponse> receiveAllTeams() throws MicroserviceAccessFailureException {
        try {
            return webClient.get()
                    .uri(WebClientConfig.TEAM_SERVICE_URL)
                    .retrieve()
                    .bodyToFlux(DetailTeamResponse.class)
                    .collectList()
                    .block();
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Team-Service", e);
            throw new MicroserviceAccessFailureException("Team");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération des équipes", e);
        }
    }
}

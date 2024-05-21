package com.attijarivos.service;

import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.dto.request.IdMembresRequest;
import com.attijarivos.dto.response.details.DetailTeamResponse;
import com.attijarivos.dto.response.shorts.ShortMembreResponse;
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
    private final WebClient.Builder webClientBuilder;

    protected void verifyTeamById(String idTeam) throws MicroserviceAccessFailureException {

        try {
            webClientBuilder.build().get().uri(WebClientConfig.TEAM_SERVICE_URL + "/" + idTeam).retrieve().bodyToMono(Object.class).block() ;
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Team-Service", e);
            throw new MicroserviceAccessFailureException("Team");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    protected boolean addMembreToTeam(String idMembre, String idTeam) throws MicroserviceAccessFailureException {
        try {
            IdMembresRequest idMembresRequest = new IdMembresRequest();
            idMembresRequest.setIdMembres(List.of(idMembre));

            Optional<DetailTeamResponse> teamResponse = Optional.ofNullable(
                    webClientBuilder.build().patch()
                    .uri(WebClientConfig.TEAM_SERVICE_URL + "/" + idTeam + "/membres")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(idMembresRequest)
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
            return webClientBuilder.build().get()
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

    protected void syncUpdateMemberInTeam(ShortMembreResponse membreResponse) throws MicroserviceAccessFailureException {
        try {
             webClientBuilder.build().patch()
                    .uri(WebClientConfig.TEAM_SERVICE_URL + "/syncUpdateMembre")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(membreResponse)
                     .retrieve()
                     .bodyToMono(Void.class)
                     .subscribe();
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Team-Service", e);
            throw new MicroserviceAccessFailureException("Team");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération des équipes", e);
        }
    }

    protected void syncDeleteMemberInTeam(String membreId) throws MicroserviceAccessFailureException {
        try {
            webClientBuilder.build().patch()
                    .uri(WebClientConfig.TEAM_SERVICE_URL + "/syncDeleteMembre/"+membreId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .subscribe();
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Team-Service lors de synchronisation les données du  membres des équipes (Delete)", e);
            throw new MicroserviceAccessFailureException("Team");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Erreur lors de la récupération des équipes", e);
        }
    }
}

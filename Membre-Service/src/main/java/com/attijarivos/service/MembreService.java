package com.attijarivos.service;

import com.attijarivos.dto.request.MembreRequest;
import com.attijarivos.dto.request.MembreUpdateRequest;
import com.attijarivos.dto.response.shorts.ShortMembreResponse;
import com.attijarivos.dto.response.details.DetailMembreResponse;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RededicationMembreException;
import com.attijarivos.exception.RequiredDataException;
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

    public MembreService(WebClient.Builder webClientBuilder, MembreRespository membreRespository, MembreMapper membreMapper) {
        super(webClientBuilder);
        this.membreRespository = membreRespository;
        this.membreMapper = membreMapper;
    }

    private Membre receiveMembre(String idMembre) throws NotFoundDataException {
        Optional<Membre> membre = membreRespository.findById(idMembre);

        if(membre.isEmpty()) throw new NotFoundDataException(idMembre);
        return membre.get();
    }

    private Boolean isNotNullValue(Object value) {
        return value == null || Objects.equals(value, "");
    }

    private void verifyDataMembre(MembreRequest membreRequest) throws RequiredDataException, RededicationMembreException {

        if(isNotNullValue(membreRequest.getNomMembre())) {
            throw new RequiredDataException("Nom est obligatoire pour l'ajout d'un nouveau membre");
        }
        if(isNotNullValue(membreRequest.getPrenom())) {
            throw new RequiredDataException("Prénom est obligatoire pour l'ajout d'un nouveau membre");
        }
        if(membreRequest.getRoleHabilation()== null ) {
            throw new RequiredDataException("Rôle d'habilation est obligatoire pour l'ajout d'un nouveau membre");
        }

        if(! membreRespository.findByNomMembreAndPrenom(
                membreRequest.getNomMembre(), membreRequest.getPrenom()).isEmpty()
        ) {
            throw new RededicationMembreException("nom et prénom");
        }
    }

    public ShortMembreResponse createMembre(MembreRequest membreRequest) throws MicroserviceAccessFailureException, RequiredDataException, RededicationMembreException {

        verifyDataMembre(membreRequest);

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

    public List<ShortMembreResponse> getAllMembres() {
        List<Membre> membres = membreRespository.findAll();
        log.info("Membres tourvés sont : {}",membres);
        return membres.stream().map(membreMapper::fromMembreToRes).toList();
    }

    public DetailMembreResponse getOneMembreDetail(String idMembre) throws NotFoundDataException, MicroserviceAccessFailureException {
        Optional<Membre> membre = Optional.of(receiveMembre(idMembre));

        return membreMapper.fromMembreToDetail(
                membre.get(),  receiveAllTeams()
        );
    }

    public DetailMembreResponse updateMembre(String idMembre, MembreUpdateRequest membreRequest) throws NotFoundDataException, MicroserviceAccessFailureException, RequiredDataException, RededicationMembreException {
        Optional<Membre> membre = Optional.of(receiveMembre(idMembre));

        verifyDataMembre(membreMapper.fromReqToUpdateMembre(membreRequest));

        membre.get().setNomMembre(membreRequest.getNomMembre());
        membre.get().setPrenom(membreRequest.getPrenom());
        membre.get().setRoleHabilation(membreRequest.getRoleHabilation());

        membreRespository.save(membre.get());

        syncUpdateMemberInTeam(
                membreMapper.fromMembreToRes(membre.get())
        );

        return membreMapper.fromMembreToDetail(
               membre.get(),  receiveAllTeams()
        );
    }

    public void deleteMembre(String idMembre) throws NotFoundDataException, MicroserviceAccessFailureException {
        Optional<Membre> membre = Optional.of(receiveMembre(idMembre));
        membreRespository.delete(membre.get());
        syncDeleteMemberInTeam(idMembre);
    }

}

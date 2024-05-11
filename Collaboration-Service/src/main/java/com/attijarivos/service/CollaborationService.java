package com.attijarivos.service;


import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.request.JoinInvitationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.mapper.CollaborationMapper;
import com.attijarivos.model.Collaboration;
import com.attijarivos.repository.CollaborationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service("service-layer-collaboration")
@RequiredArgsConstructor
@Slf4j
public class CollaborationService implements ICollaborationService<CollaborationRequest,CollaborationResponse,Long> {

    @Qualifier("mapper-layer-collaboration")
    private final CollaborationMapper collaborationMapper;
    private final CollaborationRepository collaborationRepository;
    private final WebClient webClient;

    private Optional<MembreResponse> receiveMembreById(String idMembre) throws MicroserviceAccessFailureException {

        try {
            return Optional.ofNullable(
                    webClient.get().uri(WebClientConfig.MEMBRE_SERVICE_URL + "/"+ idMembre).retrieve().bodyToFlux(MembreResponse.class).blockLast()
            );
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Membre-Service", e);
            throw new MicroserviceAccessFailureException("Membre");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    private void verifyDataCollaboration(CollaborationRequest collaborationRequest, String context) throws RequiredDataException {
        String object = "la collaboration";

        if(isNotNullValue(collaborationRequest.getTitre())) {
            log.warn("Titre est obligatoire pour "+context+" "+object);
            throw new RequiredDataException("Titre",context,object);
        }

        if(isNotNullValue(collaborationRequest.getIdProprietaire())) {
            log.warn("Identifiant de propriétaire est obligatoire pour "+context+" "+object);
            throw new RequiredDataException("Titre",context,object);
        }

        if(collaborationRequest.getConfidentielle() == null) {
            log.warn("Confidentialité est obligatoire pour "+context+" "+object);
            throw new RequiredDataException("Confidentialité",context,object);
        }
    }

    @Override
    public CollaborationResponse createOne(CollaborationRequest collaborationRequest) throws RequiredDataException, MicroserviceAccessFailureException, NotFoundDataException {

        verifyDataCollaboration(collaborationRequest,"l'ajout");

        // vérification le propriétaire (membre) au niveau de base de données
        if(receiveMembreById(collaborationRequest.getIdProprietaire()).isEmpty())
            throw new NotFoundDataException("Proriétaire",collaborationRequest.getIdProprietaire());

        Collaboration collaboration = collaborationMapper.fromReqToModel(collaborationRequest);

        // insertion les valeurs par défaut de colonne
        collaboration.setDateCreationCollaboration(new Date());
        collaboration.setVisible(true);

        // vérifier l'existance de date de départ
        if (isNotNullValue(collaboration.getDateDepart()) )
            collaboration.setDateDepart(collaboration.getDateCreationCollaboration());

        log.info("Collaboration en ligne est enregistrée : {}", collaboration);

        return collaborationMapper.fromModelToRes(
                collaborationRepository.save(collaboration)
        );
    }

    @Override
    public List<CollaborationResponse> getAll() {
        List<Collaboration> collaborations = collaborationRepository.findAll();
        log.info("Collaborations tourvées sont : {}",collaborations);
        return collaborations.stream().map(collaborationMapper::fromModelToRes).toList();
    }

    @Override
    public CollaborationResponse getOne(Long idCollaboration) throws NotFoundDataException {
        Optional<Collaboration> collaboration = collaborationRepository.findById(idCollaboration);
        log.info("Collaboration tourvée est : {}",collaboration);
        if(collaboration.isEmpty()) throw new NotFoundDataException("Collaboration",idCollaboration);
        return collaboration.map(collaborationMapper::fromModelToRes).orElse(null);
    }

    @Override
    public CollaborationResponse update(Long idCollaboration, CollaborationRequest collaborationRequest) throws RequiredDataException, NotFoundDataException {

        Optional<Collaboration> collaborationSearched = collaborationRepository.findById(idCollaboration);
        log.info("Collaboration tourvée est : {}",collaborationSearched);

        if(collaborationSearched.isEmpty()) throw new NotFoundDataException("Collaboration",idCollaboration);

        // vérification le titre et la confidentialité
        verifyDataCollaboration(collaborationRequest,"le mise à jour");

        // update les valeurs
        collaborationSearched.get().setTitre(collaborationRequest.getTitre());
        collaborationSearched.get().setConfidentielle(collaborationRequest.getConfidentielle());
        collaborationSearched.get().setDateDepart(collaborationRequest.getDateDepart());

        // vérifier l'existance de date de départ
        if (isNotNullValue(collaborationRequest.getDateDepart()))
            collaborationSearched.get().setDateDepart(collaborationSearched.get().getDateCreationCollaboration());

        log.info("Collaboration en ligne est modifiée : "+collaborationSearched.get());

        return collaborationMapper.fromModelToRes(
                collaborationRepository.save(collaborationSearched.get())
        );
    }

    @Override
    public void delete(Long idCollaboration) throws NotFoundDataException {
        Optional<Collaboration> collaboration = collaborationRepository.findById(idCollaboration);

        if(collaboration.isEmpty()) throw new NotFoundDataException("Collaboration",idCollaboration);
        collaborationRepository.delete(collaboration.get());
        log.info("Collaboration d'id est bien supprimée : {}",idCollaboration);
    }

    @Override
    public CollaborationResponse rejoindre(Long aLong, JoinInvitationRequest joinRequest) throws NotFoundDataException, RequiredDataException {
        return null;
    }
}

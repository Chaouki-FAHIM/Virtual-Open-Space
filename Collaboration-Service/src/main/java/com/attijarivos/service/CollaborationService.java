package com.attijarivos.service;


import com.attijarivos.DTO.CollaborationRequest;
import com.attijarivos.DTO.CollaborationResponse;
import com.attijarivos.DTO.MembreResponse;
import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.NotValidDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.mapper.CollaborationMapper;
import com.attijarivos.model.Collaboration;
import com.attijarivos.repository.CollaborationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service("service-layer-collaboration")
@RequiredArgsConstructor
@Slf4j
public class CollaborationService implements IService<CollaborationRequest, CollaborationResponse,Long> {

    @Qualifier("mapper-layer-collaboration")
    private final CollaborationMapper collaborationMapper;
    private final CollaborationRepository collaborationRepository;
    private final WebClient webClient;

    private Optional<MembreResponse> receiveMembreById(String idMembre) {

        try {
            return Optional.ofNullable(
                    webClient.get().uri(WebClientConfig.MEMBRE_SERVICE_URL + "/"+ idMembre).retrieve().bodyToFlux(MembreResponse.class).blockLast()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    private void verifyDataCollaboration(CollaborationRequest collaborationRequest, String context) throws RequiredDataException {

        if(isNotNullValue(collaborationRequest.getTitre())) {
            String errorMsg = "Titre est obligatoire pour "+context+" d'une collaboration en ligne";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

        if(isNotNullValue(collaborationRequest.getIdProprietaire())) {
            String errorMsg = "Identifiant de propriétaire est obligatoire pour "+context+" d'une collaboration en ligne";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

        if(collaborationRequest.getConfidentielle() == null) {
            String errorMsg = "Confidentialité est obligatoire pour "+context+" d'une collaboration en ligne";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }
    }

    @Override
    public CollaborationResponse create(CollaborationRequest collaborationRequest) throws RequiredDataException, NotValidDataException {

        verifyDataCollaboration(collaborationRequest,"l'ajout");

        // vérification le propriétaire (membre) au niveau de base de données
        if(receiveMembreById(collaborationRequest.getIdProprietaire()).isEmpty())
            throw new NotValidDataException("Proriétaire est introuvable");

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
}

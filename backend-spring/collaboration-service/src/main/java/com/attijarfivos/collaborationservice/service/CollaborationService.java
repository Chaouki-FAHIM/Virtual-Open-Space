package com.attijarfivos.collaborationservice.service;

import com.attijarfivos.collaborationservice.DTO.CollaborationRequest;
import com.attijarfivos.collaborationservice.DTO.CollaborationResponse;
import com.attijarfivos.collaborationservice.exception.NotFoundDataException;
import com.attijarfivos.collaborationservice.exception.RequiredDataException;
import com.attijarfivos.collaborationservice.mapper.CollaborationMapper;
import com.attijarfivos.collaborationservice.model.Collaboration;
import com.attijarfivos.collaborationservice.repository.CollaborationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("collaboration")
@RequiredArgsConstructor
@Slf4j
public class CollaborationService implements IService<CollaborationRequest, CollaborationResponse,Long> {

    private final CollaborationRepository collaborationRepository;

    private final CollaborationMapper collaborationMapper;

    @Override
    public CollaborationResponse create(CollaborationRequest collaborationRequest) throws RequiredDataException {

        // vérification l'existance de la valeur de titre
        if(collaborationRequest.getTitre() == null || Objects.equals(collaborationRequest.getTitre(), "")) {
            String errorMsg = "Titre est obligatoire pour l'ajout d'une nouvelle collaboration en ligne";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

        // vérification l'existance de la valeur de confidentielle
        if(collaborationRequest.getConfidentielle() == null) {
            String errorMsg = "Confidentialité de la collaboration est obligatoire pour leur ajout";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

        // vérification les emails de membre


        Collaboration collaboration = collaborationMapper.fromReqToCollaboration (collaborationRequest);

        // insertion les valeurs par défaut de colonne
        collaboration.setDateCreation(new Date());
        collaboration.setVisible(true);

        // vérifier l'existance de date de départ
        if (collaboration.getDateDepart() == null || Objects.equals(collaboration.getDateDepart(), ""))
            collaboration.setDateDepart(collaboration.getDateCreation());


        log.info("Collaboration en ligne enregsitrer est enregistrée : "+collaboration.toString());

        return collaborationMapper.fromCollaborationToRes(
                collaborationRepository.save(collaboration)
        );
    }

    @Override
    public List<CollaborationResponse> getAll() {
        List<Collaboration> collaborations = collaborationRepository.findAll();
        log.info("Collaborations tourvées sont : {}",collaborations);
        return collaborations.stream().map(collaborationMapper::fromCollaborationToRes).toList();
    }

    @Override
    public CollaborationResponse getOne(Long id) throws NotFoundDataException {
        Optional<Collaboration> collaboration = collaborationRepository.findById(id);
        log.info("Collaboration tourvée est : {}",collaboration);
        if(collaboration.isEmpty()) throw new NotFoundDataException(id);
        return collaboration.map(collaborationMapper::fromCollaborationToRes).orElse(null);
    }

    @Override
    public CollaborationResponse update(Long id, CollaborationRequest collaborationRequest) throws RequiredDataException, NotFoundDataException {

        Optional<Collaboration> collaborationSearched = collaborationRepository.findById(id);
        log.info("Collaboration tourvée est : {}",collaborationSearched);

        if(collaborationSearched.isEmpty()) throw new NotFoundDataException(id);

        // vérification l'existance de la valeur de titre
        if(collaborationRequest.getTitre() == null || Objects.equals(collaborationRequest.getTitre(), "")) {
            String errorMsg = "Titre est obligatoire pour le mise à jour d'une nouvelle collaboration en ligne";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

        // vérification l'existance de la valeur de confidentielle
        if(collaborationRequest.getConfidentielle() == null) {
            String errorMsg = "Confidentialité de la collaboration est obligatoire pour leur mise à jour";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

        // vérification les emails de membre


        // update les valeurs
        collaborationSearched.get().setTitre(collaborationRequest.getTitre());
        collaborationSearched.get().setConfidentielle(collaborationRequest.getConfidentielle());
        collaborationSearched.get().setDateDepart(collaborationRequest.getDateDepart());


        // vérifier l'existance de date de départ
        if (collaborationRequest.getDateDepart() == null || Objects.equals(collaborationRequest.getDateDepart(), ""))
            collaborationSearched.get().setDateDepart(collaborationSearched.get().getDateCreation());


        log.info("Collaboration en ligne est modifiée : "+collaborationSearched.get());

        return collaborationMapper.fromCollaborationToRes(
                collaborationRepository.save(collaborationSearched.get())
        );
    }

    @Override
    public void delete(Long id) throws NotFoundDataException {
        Optional<Collaboration> collaboration = collaborationRepository.findById(id);

        if(collaboration.isEmpty()) throw new NotFoundDataException(id);
        collaborationRepository.delete(collaboration.get());
        log.info("Collaboration d'id est bien supprimée : {}",id);
    }
}

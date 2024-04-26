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
            log.warn("Titre est obligatoire pour l'ajout d'une nouvelle collaboration en ligne");
            throw new RequiredDataException("Titre est obligatoire pour l'ajout d'une nouvelle collaboration en ligne");
        }

        // vérification l'existance de la valeur de confidentielle
        if(collaborationRequest.getConfidentielle() == null) {
            throw new RequiredDataException("Confidentialité de la collaboration est obligatoire pour leur ajout");
        }

        // vérification les emails de membre


        Collaboration collaboration = collaborationMapper.fromReqToCollaboration (collaborationRequest);


        // insertion les valeurs par défaut de colonne
        collaboration.setDateCreation(new Date());
        collaboration.setVisible(true);

        // vérifier l'existance de date de départ
        if (collaboration.getDateDepart() == null || Objects.equals(collaboration.getDateDepart(), ""))
            collaboration.setDateDepart(collaboration.getDateCreation());


        collaborationRepository.save(collaboration);

        log.info("Collaboration d'id {} est enregistrée!!",collaboration.getId());

        return collaborationMapper.fromCollaborationToRes(collaboration);
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
    public CollaborationResponse update(Long id, CollaborationRequest collaborationRequest) {
        return null;
    }

    @Override
    public void delete(Long id) throws NotFoundDataException {
        Optional<Collaboration> collaboration = collaborationRepository.findById(id);

        if(collaboration.isEmpty()) throw new NotFoundDataException(id);
        collaborationRepository.delete(collaboration.get());
        log.info("Collaboration d'id est bien supprimée : {}",id);
    }
}

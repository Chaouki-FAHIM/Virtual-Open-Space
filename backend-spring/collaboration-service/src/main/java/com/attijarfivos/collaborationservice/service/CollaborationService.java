package com.attijarfivos.collaborationservice.service;

import com.attijarfivos.collaborationservice.DTO.CollaborationRequest;
import com.attijarfivos.collaborationservice.DTO.CollaborationResponse;
import com.attijarfivos.collaborationservice.exception.RequiredDataException;
import com.attijarfivos.collaborationservice.mapper.CollaborationMapper;
import com.attijarfivos.collaborationservice.model.Collaboration;
import com.attijarfivos.collaborationservice.repository.CollaborationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("collaboration")
@RequiredArgsConstructor
@Slf4j
public class CollaborationService implements IService<CollaborationRequest, CollaborationResponse,Long> {

    private final CollaborationRepository collaborationRepository;

    private final CollaborationMapper collaborationMapper;

    @Override
    public CollaborationResponse create(CollaborationRequest collaborationRequest) throws RequiredDataException {

        if(collaborationRequest.getTitre() == null  || Objects.equals(collaborationRequest.getTitre(), "")) {
            throw new RequiredDataException("Titre est obligatoire pour l'ajout d'un nouveau collaboration en ligne");
        }

        if(collaborationRequest.getConfidentielle() == null) {
            throw new RequiredDataException("Confidentialité de collaboration est obligatoire pour leur ajout");
        }

        Collaboration collaboration = collaborationMapper.fromReqToCollaboration (collaborationRequest);
        collaborationRepository.save(collaboration);

        log.info("Collaboration d'id {} est enregistré!!",collaboration.getId());

        return collaborationMapper.fromCollaborationToRes(collaboration);
    }

    @Override
    public List<CollaborationResponse> getAll() {
        return null;
    }

    @Override
    public CollaborationResponse getOne(Long aLong) {
        return null;
    }

    @Override
    public CollaborationResponse update(Long aLong, CollaborationRequest collaborationRequest) {
        return null;
    }

    @Override
    public CollaborationResponse update(Long aLong) {
        return null;
    }
}

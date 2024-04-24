package com.attijarfivos.collaborationservice.mapper;

import com.attijarfivos.collaborationservice.DTO.CollaborationRequest;
import com.attijarfivos.collaborationservice.DTO.CollaborationResponse;
import com.attijarfivos.collaborationservice.model.Collaboration;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CollaborationMapper {

    public Collaboration fromReqToCollaboration(CollaborationRequest collaborationRequest) {

        return Collaboration.builder()
                .titre(collaborationRequest.getTitre())
                .proprietaire(collaborationRequest.getProprietaire())
                .dateCreation(collaborationRequest.getDateCreation())
                .dateDepart(collaborationRequest.getDateDepart())
                .confidentielle(collaborationRequest.getConfidentielle())
                .invites(collaborationRequest.getInvites())
                .visible(collaborationRequest.getVisible())
                .build();
    }

    public CollaborationResponse fromCollaborationToRes(Collaboration collaboration) {
        CollaborationResponse collaborationResponse = new CollaborationResponse();
        BeanUtils.copyProperties(collaboration, collaborationResponse);
        return collaborationResponse;
    }
}

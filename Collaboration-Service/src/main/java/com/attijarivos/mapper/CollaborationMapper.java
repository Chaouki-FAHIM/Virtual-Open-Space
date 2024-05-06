package com.attijarivos.mapper;


import com.attijarivos.DTO.CollaborationRequest;
import com.attijarivos.DTO.CollaborationResponse;
import com.attijarivos.model.Collaboration;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CollaborationMapper {

    public Collaboration fromReqToCollaboration(CollaborationRequest collaborationRequest) {

        return Collaboration.builder()
                .titre(collaborationRequest.getTitre())
                .proprietaire(collaborationRequest.getProprietaire())
                .dateDepart(collaborationRequest.getDateDepart())
                .confidentielle(collaborationRequest.getConfidentielle())
                .invites(collaborationRequest.getInvites())
                .build();
    }

    public CollaborationResponse fromCollaborationToRes(Collaboration collaboration) {
        CollaborationResponse collaborationResponse = new CollaborationResponse();
        BeanUtils.copyProperties(collaboration, collaborationResponse);
        return collaborationResponse;
    }
}

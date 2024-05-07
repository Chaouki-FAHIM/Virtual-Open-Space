package com.attijarivos.mapper;


import com.attijarivos.DTO.InvitationRequest;
import com.attijarivos.DTO.InvitationResponse;

import com.attijarivos.model.Collaboration;
import com.attijarivos.model.Invitation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InvitationMapper  {

    private final CollaborationMapper collaborationMapper;


    public Invitation fromReqToInvitation(InvitationRequest invitationRequest, Collaboration collaboration) {
        return Invitation.builder()
                .collaboration(collaboration)
                .idInvite(invitationRequest.getIdInvite())
                .build();
    }

    public InvitationResponse fromInvitationToRes(Invitation invitation) {
        return InvitationResponse.builder()
                .idInvitation(invitation.getIdInvitation())
                .collaboration(collaborationMapper.fromCollaborationToRes(invitation.getCollaboration()))
                .idInvite(invitation.getIdInvite())
                .dateCreationInvitation(invitation.getDateCreationInvitation())
                .dateParticiaption(invitation.getDateParticiaption())
                .build();
    }
}

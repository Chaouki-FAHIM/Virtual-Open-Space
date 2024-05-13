package com.attijarivos.mapper;


import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.DTO.response.InvitationResponse;


import com.attijarivos.model.Invitation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component("mapper-layer-invitation")
@RequiredArgsConstructor
public class InvitationMapper implements IMapper<Invitation,InvitationRequest,InvitationResponse>  {

    private final CollaborationMapper collaborationMapper;

    @Override
    public Invitation fromReqToModel(InvitationRequest invitationRequest) {
        return Invitation.builder()
                .idInvite(invitationRequest.getIdInvite())
                .build();
    }

    @Override
    public InvitationResponse fromModelToRes(Invitation invitation) {
        return InvitationResponse.builder()
                .idInvitation(invitation.getIdInvitation())
                .collaboration(collaborationMapper.fromModelToRes(invitation.getCollaboration()))
                .idInvite(invitation.getIdInvite())
                .dateCreationInvitation(invitation.getDateCreationInvitation())
                .dateParticiaption(invitation.getDateParticiaption())
                .build();
    }
}

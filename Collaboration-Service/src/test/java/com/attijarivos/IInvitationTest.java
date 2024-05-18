package com.attijarivos;


import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.DTO.response.InvitationResponse;
import com.attijarivos.model.Invitation;

import java.util.Date;

public interface IInvitationTest extends ICollaborationTest {

    default Invitation getInvitation(long idInvitation,long idCollaboration) {
        return Invitation.builder()
                .idInvitation(idInvitation)
                .idInvite(SECOND_MEMBRE_ID)
                .dateCreationInvitation(new Date())
                .collaboration(getCollaboration(idCollaboration))
                .build();
    }

    default InvitationRequest getInvitationRequest() {
       return InvitationRequest.builder()
                .idInvite(SECOND_MEMBRE_ID)
                .idCollaboration(1L)
                .build();
    }

    default InvitationResponse getInvitationResponse (long idInvitation, InvitationRequest request) {

        CollaborationResponse collaborationResponse = getCollaborationResponse(1L, getCollaborationRequest());
        return InvitationResponse.builder()
                .idInvitation(idInvitation)
                .dateCreationInvitation(new Date())
                .idInvite(request.getIdInvite())
                .collaboration(collaborationResponse)
                .build();
    }

}

package com.attijarivos;


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

}

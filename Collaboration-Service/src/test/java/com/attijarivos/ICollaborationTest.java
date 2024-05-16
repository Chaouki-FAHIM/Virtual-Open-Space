package com.attijarivos;

import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.model.Collaboration;

import java.util.Date;
import java.util.Random;

public interface ICollaborationTest extends DataTest {

    default CollaborationRequest getCollaborationRequest() {
        return CollaborationRequest.builder()
                .confidentielle(true)
                .dateDepart(new Date())
                .titre("Collaboration Test")
                .idProprietaire(FIRST_MEMBRE_ID)
                .build();
    }

    default Collaboration getCollaboration(long idCollaboration) {
        return Collaboration.builder()
                .idCollaboration(idCollaboration)
                .confidentielle(new Random().nextBoolean())
                .dateDepart(new Date())
                .titre("Collaboration Test")
                .idProprietaire(FIRST_MEMBRE_ID)
                .build();
    }

    default CollaborationUpdateRequest getCollaborationUpdateRequest() {
        return CollaborationUpdateRequest.builder()
                .titre("Collaboration Test Updated")
                .confidentielle(false)
                .dateDepart(new Date())
                .build();
    }
}

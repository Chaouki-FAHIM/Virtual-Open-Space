package com.attijarivos;

import com.attijarivos.DTO.request.CollaborationCreateRequest;
import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.model.Collaboration;

import java.util.Date;
import java.util.Random;

public interface ICollaborationTest extends DataTest {

    default CollaborationCreateRequest getCollaborationRequest() {
        return CollaborationCreateRequest.builder()
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

    default CollaborationResponse getCollaborationResponse(long idCollaboration, CollaborationCreateRequest request) {
        return CollaborationResponse.builder()
                .idCollaboration(idCollaboration)
                .idProprietaire(request.getIdProprietaire())
                .dateCreationCollaboration(new Date())
                .dateDepart(request.getDateDepart())
                .titre(request.getTitre())
                .confidentielle(request.getConfidentielle())
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

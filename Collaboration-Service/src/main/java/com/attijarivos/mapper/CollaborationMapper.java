package com.attijarivos.mapper;


import com.attijarivos.DTO.request.CollaborationCreateRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.model.Collaboration;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component("mapper-layer-collaboration")
public class CollaborationMapper implements IMapper<Collaboration, CollaborationCreateRequest,CollaborationResponse> {

    @Override
    public Collaboration fromReqToModel(CollaborationCreateRequest collaborationCreateRequest) {
        return Collaboration.builder()
                .titre(collaborationCreateRequest.getTitre())
                .idProprietaire(collaborationCreateRequest.getIdProprietaire())
                .dateDepart(collaborationCreateRequest.getDateDepart())
                .confidentielle(collaborationCreateRequest.getConfidentielle())
                .build();
    }

    @Override
    public CollaborationResponse fromModelToRes(Collaboration collaboration) {
        CollaborationResponse collaborationResponse = new CollaborationResponse();
        BeanUtils.copyProperties(collaboration, collaborationResponse);
        return collaborationResponse;
    }
}

package com.attijarivos.mapper;


import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.model.Collaboration;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component("mapper-layer-collaboration")
public class CollaborationMapper implements IMapper<Collaboration,CollaborationRequest,CollaborationResponse> {

    @Override
    public Collaboration fromReqToModel(CollaborationRequest collaborationRequest) {
        return Collaboration.builder()
                .titre(collaborationRequest.getTitre())
                .idProprietaire(collaborationRequest.getIdProprietaire())
                .dateDepart(collaborationRequest.getDateDepart())
                .confidentielle(collaborationRequest.getConfidentielle())
                .build();
    }

    @Override
    public CollaborationResponse fromModelToRes(Collaboration collaboration) {
        CollaborationResponse collaborationResponse = new CollaborationResponse();
        BeanUtils.copyProperties(collaboration, collaborationResponse);
        return collaborationResponse;
    }
}

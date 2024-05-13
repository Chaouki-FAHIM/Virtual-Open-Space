package com.attijarivos.mapper;

import com.attijarivos.DTO.request.ParticipationRequest;
import com.attijarivos.DTO.response.ParticipationResponse;
import com.attijarivos.model.Participation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("mapper-layer-participation")
@RequiredArgsConstructor
public class ParticipationMapper implements IMapper<Participation, ParticipationRequest, ParticipationResponse> {

    private final CollaborationMapper collaborationMapper;


    @Override
    public Participation fromReqToModel(ParticipationRequest participationRequest) {
        return Participation.builder()
                .idParticipant(participationRequest.getIdParticipant())
                .build();
    }

    @Override
    public ParticipationResponse fromModelToRes(Participation participation) {
        return ParticipationResponse.builder()
                .collaboration(collaborationMapper.fromModelToRes(participation.getCollaboration()))
                .idParticipant(participation.getIdParticipant())
                .dateParticiaption(participation.getDateParticiaption())
                .build();
    }
}

package com.attijarivos.service;

import com.attijarivos.DTO.*;
import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.NotValidDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.mapper.IMapper;
import com.attijarivos.model.Collaboration;
import com.attijarivos.model.Invitation;
import com.attijarivos.repository.CollaborationRepository;
import com.attijarivos.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("service-layer-invitation")
@RequiredArgsConstructor
@Slf4j
public class InvitationService implements IService<InvitationRequest, InvitationResponse,Long> {

    @Qualifier("mapper-layer-invitation")
    private final IMapper<Invitation,InvitationRequest,InvitationResponse> invitationMapper;
    private final InvitationRepository invitationRepository;
    private final WebClient webClient;
    private final CollaborationRepository collaborationRepository;

    private Optional<MembreResponse> receiveInviteById(String membreId) {

        return Optional.ofNullable(
                webClient.get().uri(WebClientConfig.MEMBRE_SERVICE_URL + "/"+ membreId).retrieve().bodyToFlux(MembreResponse.class).blockLast()
        );
    }

    private void verifyDataInvitation(InvitationRequest invitationRequest, String context) throws RequiredDataException {

        if(isNotNullValue(invitationRequest.getIdInvite())) {
            String errorMsg = "Identifiant d'invité (membre) est obligatoire pour "+context+" d'une invitation à la collaboration en ligne";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

        if(isNotNullValue(invitationRequest.getIdCollaboration())) {
            String errorMsg = "Identifiant d'invité est obligatoire pour "+context+" d'une invitation à la collaboration en ligne";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

    }

    @Override
    public InvitationResponse create(InvitationRequest invitationRequest) throws RequiredDataException, NotValidDataException, NotFoundDataException {

        // vérification l'id d'invité et l'id de la collaboration
        verifyDataInvitation(invitationRequest,"la création");

        // vérification l'invité (membre)
        if(receiveInviteById(invitationRequest.getIdInvite()).isEmpty())
            throw new NotValidDataException("Invité est introuvable");

        Collaboration collaboration = collaborationRepository.findByIdCollaboration(invitationRequest.getIdCollaboration());

        if(collaboration == null)
            throw new NotFoundDataException("Invitation",invitationRequest.getIdCollaboration());
        Invitation invitation = invitationMapper.fromReqToModel(invitationRequest);

        invitation.setDateCreationInvitation(new Date());
        invitation.setCollaboration(collaboration);

        log.info("Invitation de la collaboration en ligne est enregistrée : {}", invitation);

        return invitationMapper.fromModelToRes(
                invitationRepository.save(invitation)
        );
    }

    @Override
    public List<InvitationResponse> getAll() {
        return List.of();
    }

    @Override
    public InvitationResponse getOne(Long aLong) throws NotFoundDataException {
        return null;
    }

    @Override
    public InvitationResponse update(Long aLong, InvitationRequest invitationRequest) throws NotFoundDataException, RequiredDataException, NotValidDataException {
        return null;
    }

    @Override
    public void delete(Long aLong) throws NotFoundDataException, RequiredDataException, NotValidDataException {

    }
}

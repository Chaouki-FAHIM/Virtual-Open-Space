package com.attijarivos.service;

import com.attijarivos.DTO.InvitationRequest;
import com.attijarivos.DTO.InvitationResponse;
import com.attijarivos.DTO.MembreResponse;
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

    private Optional<MembreResponse> receiveInviteById(String idInvite) {
        try {
            return Optional.ofNullable(
                    webClient.get().uri(WebClientConfig.MEMBRE_SERVICE_URL + "/"+ idInvite).retrieve().bodyToFlux(MembreResponse.class).blockLast()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    private void verifyDataInvitation(InvitationRequest invitationRequest, String context) throws RequiredDataException {

        if(isNotNullValue(invitationRequest.getIdInvite())) {
            String errorMsg = "Identifiant d'invité (membre) est obligatoire pour "+context+" d'une invitation";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

        if(isNotNullValue(invitationRequest.getIdCollaboration())) {
            String errorMsg = "Identifiant de la collaboration est obligatoire pour "+context+" d'une invitation";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

    }

    @Override
    public InvitationResponse create(InvitationRequest invitationRequest) throws RequiredDataException, NotValidDataException, NotFoundDataException {

        verifyDataInvitation(invitationRequest,"la création");

        // vérification l'invité (membre) au niveau de base de données
        if(receiveInviteById(invitationRequest.getIdInvite()).isEmpty())
            throw new NotValidDataException("Invité est introuvable");

        Collaboration collaboration = collaborationRepository.findByIdCollaboration(invitationRequest.getIdCollaboration());

        if(collaboration == null)
            throw new NotFoundDataException("Collaboration",invitationRequest.getIdCollaboration());
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
        List<Invitation> invitations = invitationRepository.findAll();
        log.info("Invitations de la collaboration en ligne tourvées sont : {}",invitations);
        return invitations.stream().map(invitationMapper::fromModelToRes).toList();
    }

    @Override
    public InvitationResponse getOne(Long idInvitation) throws NotFoundDataException {
        Optional<Invitation> invitation = invitationRepository.findById(idInvitation);
        log.info("Invitation tourvée est : {}",invitation);
        if(invitation.isEmpty()) throw new NotFoundDataException("Invitation",idInvitation);
        return invitation.map(invitationMapper::fromModelToRes).orElse(null);
    }

    @Override
    public InvitationResponse update(Long aLong, InvitationRequest invitationRequest) throws NotFoundDataException, RequiredDataException, NotValidDataException {
        return null;
    }

    @Override
    public void delete(Long aLong) throws NotFoundDataException, RequiredDataException, NotValidDataException {

    }
}

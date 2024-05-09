package com.attijarivos.service;

import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.DTO.response.InvitationResponse;
import com.attijarivos.DTO.request.InvitationUpdateRequest;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.NotValidDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.mapper.IMapper;
import com.attijarivos.model.Collaboration;
import com.attijarivos.model.Invitation;
import com.attijarivos.repository.CollaborationRepository;
import com.attijarivos.repository.InvitationRepository;
import jakarta.transaction.Transactional;
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
public class InvitationService implements IServiceInvitation<InvitationRequest, InvitationUpdateRequest, InvitationResponse,Long> {

    @Qualifier("mapper-layer-invitation")
    private final IMapper<Invitation,InvitationRequest,InvitationResponse> invitationMapper;
    private final InvitationRepository invitationRepository;
    private final CollaborationRepository collaborationRepository;
    private final WebClient webClient;
    private int numbreOfInvitationProcessedForCreateList = 0;

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
            String errorMsg = "Identifiant d'invité (membre) est obligatoire pour "+context+" d'invitation";
            if(numbreOfInvitationProcessedForCreateList >0)  errorMsg= errorMsg+" numéro "+ numbreOfInvitationProcessedForCreateList;
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

        if(isNotNullValue(invitationRequest.getIdCollaboration())) {
            String errorMsg = "Identifiant de la collaboration est obligatoire pour "+context+" d'invitation";
            if(numbreOfInvitationProcessedForCreateList >0)  errorMsg= errorMsg+" numéro "+ numbreOfInvitationProcessedForCreateList;
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

    }

    @Override
    public InvitationResponse createOne(InvitationRequest invitationRequest) throws RequiredDataException, NotValidDataException, NotFoundDataException {

        log.error("numbreOfInvitationProcessedForCreateList : " + numbreOfInvitationProcessedForCreateList);
        verifyDataInvitation(invitationRequest,"la création");

        // vérification l'invité (membre) au niveau de base de données
        if(receiveInviteById(invitationRequest.getIdInvite()).isEmpty()) {
            if (numbreOfInvitationProcessedForCreateList >0) throw new NotValidDataException("Invité est introuvable d'invitation numéro "+ numbreOfInvitationProcessedForCreateList);
            else throw new NotValidDataException("Invité est introuvable !!");
        }


        Collaboration collaboration = collaborationRepository.findByIdCollaboration(invitationRequest.getIdCollaboration());

        if(collaboration == null) {
            if (numbreOfInvitationProcessedForCreateList >0) throw new NotFoundDataException("Collaboration avec l'id "+invitationRequest.getIdCollaboration()+ " est introuvable de l'invitation numero "+ numbreOfInvitationProcessedForCreateList);
            else throw new NotFoundDataException("Collaboration", invitationRequest.getIdCollaboration());
        }

        Invitation invitation = invitationMapper.fromReqToModel(invitationRequest);

        invitation.setDateCreationInvitation(new Date());
        invitation.setCollaboration(collaboration);

        log.info("Invitation de la collaboration en ligne est enregistrée : {}", invitation);

        return invitationMapper.fromModelToRes(
                invitationRepository.save(invitation)
        );
    }

    @Transactional
    @Override
    public List<InvitationResponse> createInvitationList(List<InvitationRequest> invitationRequestList) throws RequiredDataException {

        if(invitationRequestList.isEmpty())
            throw new RequiredDataException("List des invitations est obligatoire");
        numbreOfInvitationProcessedForCreateList = 0;

        List<InvitationResponse> invitationResponseList = invitationRequestList.stream().map(invitationRequest -> {
            try {
                numbreOfInvitationProcessedForCreateList ++;
                return createOne(invitationRequest);
            } catch (RequiredDataException | NotValidDataException | NotFoundDataException e) {
                log.error("Erreur lors du traitement de l'invitation numéro " + numbreOfInvitationProcessedForCreateList);
                        numbreOfInvitationProcessedForCreateList = 0;
                throw new RuntimeException(e.getMessage());
            }
        }).toList();
        log.warn("Ajout avec succès d'une list des invitations !! ");
        numbreOfInvitationProcessedForCreateList = 0;
        return invitationResponseList;
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
    public InvitationResponse update(Long idInvitation, InvitationUpdateRequest invitationUpdateRequest) throws NotFoundDataException, RequiredDataException, NotValidDataException {
        Optional<Invitation> invitationSearched = invitationRepository.findById(idInvitation);
        log.info("Invitation tourvée est : {}",invitationSearched);

        if(invitationSearched.isEmpty()) throw new NotFoundDataException("Invitation",idInvitation);

        if(isNotNullValue(invitationUpdateRequest.getDateParticiaption())) {
            String errorMsg = "Date de participation est obligatoire pour le mise à jour d'invitation";
            log.warn(errorMsg);
            throw new RequiredDataException(errorMsg);
        }

        // update la valeur
        invitationSearched.get().setDateParticiaption(invitationUpdateRequest.getDateParticiaption());

        log.info("Invitation est modifiée : "+invitationSearched.get());

        return invitationMapper.fromModelToRes(
                invitationRepository.save(invitationSearched.get())
        );
    }

    @Override
    public void delete(Long idInvitation) throws NotFoundDataException, RequiredDataException, NotValidDataException {
        Optional<Invitation> invitation = invitationRepository.findById(idInvitation);

        if(invitation.isEmpty()) throw new NotFoundDataException("Invitation",idInvitation);
        invitationRepository.delete(invitation.get());
        log.info("Collaboration d'id est bien supprimée : {}",idInvitation);
    }
}

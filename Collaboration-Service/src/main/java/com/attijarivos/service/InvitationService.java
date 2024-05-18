package com.attijarivos.service;

import com.attijarivos.DTO.request.InvitationListRequest;
import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.DTO.response.InvitationResponse;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.exception.*;
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
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("service-layer-invitation")
@RequiredArgsConstructor
@Slf4j
public class InvitationService implements IInvitationService<InvitationRequest,InvitationResponse,Long> {

    @Qualifier("mapper-layer-invitation")
    private final IMapper<Invitation,InvitationRequest,InvitationResponse> invitationMapper;
    private final InvitationRepository invitationRepository;
    private final CollaborationRepository collaborationRepository;
    private final WebClient webClient;
    private int numbreOfInvitationProcessedForCreateList = 0;

    private Optional<MembreResponse> receiveInviteById(String idInvite) throws MicroserviceAccessFailureException {
        try {
            return Optional.ofNullable(
                    webClient.get().uri(WebClientConfig.MEMBRE_SERVICE_URL + "/"+ idInvite).retrieve().bodyToFlux(MembreResponse.class).blockLast()
            );
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Membre-Service", e);
            throw new MicroserviceAccessFailureException("Membre");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    private void verifyDataInvitation(InvitationRequest invitationRequest, String context) throws RequiredDataException {

        String object = "l'invitation";

        if(isNotNullValue(invitationRequest.getIdInvite())) {
            String errorMsg = "Identifiant d'invité (membre) est obligatoire pour "+context+" "+object;
            if(numbreOfInvitationProcessedForCreateList >0)  {
                log.warn(errorMsg+ " numéro "+ numbreOfInvitationProcessedForCreateList);
                throw new RequiredDataException("Identifiant d'invité",context,object,numbreOfInvitationProcessedForCreateList);
            }
            log.warn(errorMsg);
            throw new RequiredDataException("Identifiant d'invité",context,object);
        }

        if(isNotNullValue(invitationRequest.getIdCollaboration())) {
            String errorMsg = "Identifiant de la collaboration est obligatoire pour "+context+" "+object;
            if(numbreOfInvitationProcessedForCreateList >0)  {
                log.warn(errorMsg+ " numéro "+ numbreOfInvitationProcessedForCreateList);
                throw new RequiredDataException("Identifiant de la collaboration",context,object,numbreOfInvitationProcessedForCreateList);
            }
            log.warn(errorMsg);
            throw new RequiredDataException("Identifiant de la collaboration",context,object);
        }

    }

    private boolean isRededicationInvitation(InvitationRequest invitationRequest) {

        return invitationRepository.findAll().stream()
                .anyMatch(invitation ->
                        Objects.equals(invitation.getIdInvite(), invitationRequest.getIdInvite())
                        && Objects.equals(invitation.getCollaboration().getIdCollaboration(), invitationRequest.getIdCollaboration())

                );
    }


    @Override
    public InvitationResponse createOne(InvitationRequest invitationRequest) throws RequiredDataException, NotFoundDataException, RededicationInvitationException, NotValidOwnerInviteException, MicroserviceAccessFailureException {

        verifyDataInvitation(invitationRequest,"la création");

        // vérification l'invité (membre) au niveau de base de données
        if(receiveInviteById(invitationRequest.getIdInvite()).isEmpty()) {
            if (numbreOfInvitationProcessedForCreateList >0) throw new NotFoundDataException("Invité est introuvable d'invitation numéro "+ numbreOfInvitationProcessedForCreateList);
            else throw new NotFoundDataException("Invité",invitationRequest.getIdInvite() );
        }

        Collaboration collaboration = collaborationRepository.findByIdCollaboration(invitationRequest.getIdCollaboration());

        if(collaboration == null)
            if (numbreOfInvitationProcessedForCreateList >0) throw new NotFoundDataException("Collaboration avec l'id "+invitationRequest.getIdCollaboration()+ " est introuvable de l'invitation numero "+ numbreOfInvitationProcessedForCreateList);
            else throw new NotFoundDataException("Collaboration", invitationRequest.getIdCollaboration());


        if (isNotForOwnerOfCollaboration(collaboration.getIdProprietaire(),invitationRequest))
            if (numbreOfInvitationProcessedForCreateList > 0)
                throw new NotValidOwnerInviteException(numbreOfInvitationProcessedForCreateList);
            else throw new NotValidOwnerInviteException(collaboration.getIdProprietaire());

        if(isRededicationInvitation(invitationRequest))
            if (numbreOfInvitationProcessedForCreateList >0) throw new RededicationInvitationException(numbreOfInvitationProcessedForCreateList);
            else throw new RededicationInvitationException();

        Invitation invitation = invitationMapper.fromReqToModel(invitationRequest);

        invitation.setDateCreationInvitation(new Date());
        invitation.setCollaboration(collaboration);

        log.info("Invitation de la collaboration en ligne est enregistrée : {}", invitation);

        return invitationMapper.fromModelToRes(
                invitationRepository.save(invitation)
        );
    }

    private boolean isNotForOwnerOfCollaboration(String idProprietaire, InvitationRequest invitationRequest) {

        return invitationRepository.findAll().stream()
                .anyMatch(
                        invitation -> Objects.equals(idProprietaire,invitationRequest.getIdInvite()
                )
        );
    }

    private Optional<Invitation> receiveInvitation(Long idInvitation) throws NotFoundDataException {
        Optional<Invitation> invitation = invitationRepository.findById(idInvitation);

        if(invitation.isEmpty()) throw new NotFoundDataException("Invitation",idInvitation);
        return invitation;
    }

    @Transactional
    @Override
    public List<InvitationResponse> createInvitationList(InvitationListRequest invitationRequestList) throws RequiredDataException {

        if(invitationRequestList.getIdInvites().isEmpty())
            throw new RequiredDataException("List des invitations est obligatoire");
        numbreOfInvitationProcessedForCreateList = 0;

        List<InvitationResponse> invitationResponseList = invitationRequestList.getIdInvites().stream().map(invitationRequest -> {
            try {
                numbreOfInvitationProcessedForCreateList ++;
                return createOne(
                        InvitationRequest.builder()
                                .idInvite(invitationRequest)
                                .idCollaboration(invitationRequestList.getIdCollaboration())
                                .build());
            } catch (RequiredDataException | NotFoundDataException | RededicationInvitationException |
                     NotValidOwnerInviteException | MicroserviceAccessFailureException e) {
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
    public List<InvitationResponse> getAll() throws NotFoundDataException {
        List<Invitation> invitations = invitationRepository.findAll();
        if(invitations.isEmpty()) throw new NotFoundDataException("Liste des invitations est vide !!");
        log.info("Invitations de la collaboration en ligne tourvées sont : {}",invitations);
        return invitations.stream().map(invitationMapper::fromModelToRes).toList();
    }

    @Override
    public InvitationResponse getOne(Long idInvitation) throws NotFoundDataException {
        Optional<Invitation> invitation = receiveInvitation(idInvitation);
        log.info("Invitation tourvée est : {}",invitation);
        return invitation.map(invitationMapper::fromModelToRes).orElse(null);
    }

    @Override
    public boolean delete(Long idInvitation) throws NotFoundDataException{
        Optional<Invitation> invitation = receiveInvitation(idInvitation);
        invitationRepository.delete(invitation.get());
        log.info("Collaboration d'id est bien supprimée : {}",idInvitation);
        return true;
    }

}

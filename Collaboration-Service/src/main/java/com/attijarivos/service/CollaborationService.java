package com.attijarivos.service;


import com.attijarivos.DTO.request.CollaborationCreateRequest;
import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.request.InvitationListRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.exception.CollaborationAccessDeniedException;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.mapper.CollaborationMapper;
import com.attijarivos.model.Collaboration;
import com.attijarivos.model.Invitation;
import com.attijarivos.model.Participation;
import com.attijarivos.repository.CollaborationRepository;
import com.attijarivos.repository.InvitationRepository;
import com.attijarivos.repository.ParticipationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.*;
import java.util.stream.Collectors;


@Service("service-layer-collaboration")
@RequiredArgsConstructor
@Slf4j
public class CollaborationService implements ICollaborationService<CollaborationCreateRequest,CollaborationResponse,Long> {

    @Qualifier("mapper-layer-collaboration")
    private final CollaborationMapper collaborationMapper;
    private final CollaborationRepository collaborationRepository;
    private final ParticipationRepository participationRepository;
    private final InvitationRepository invitationRepository;
    @Qualifier("webClient-layer-config")
    private final WebClient.Builder webClientBuilder;

    private Optional<MembreResponse> receiveMembreById(String idMembre) throws MicroserviceAccessFailureException {

        try {
            return Optional.ofNullable(
                    webClientBuilder.build().get().uri(WebClientConfig.MEMBRE_SERVICE_URL + "/"+ idMembre).retrieve().bodyToMono(MembreResponse.class).block()
            );
        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Membre-Service", e);
            throw new MicroserviceAccessFailureException("Membre");
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    private List<MembreResponse> receiveAllMembres() throws MicroserviceAccessFailureException {

        try {
            return webClientBuilder.build().get().uri(WebClientConfig.MEMBRE_SERVICE_URL)
                    .retrieve().bodyToFlux(MembreResponse.class)
                    .collectList().block();

        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Membre-Service", e);
            throw new MicroserviceAccessFailureException("Membre");
        } catch (Exception e) {
            log.error(e.getMessage());
            return List.of();
        }
    }

    private void createGuestList(InvitationListRequest invitationListRequest) throws Exception {

        try {
             webClientBuilder.build().post().uri(WebClientConfig.INVITATION_SERVICE_URL+"/list")
                     .contentType(MediaType.APPLICATION_JSON)
                     .bodyValue(invitationListRequest)
                     .retrieve()
                     .bodyToMono(Void.class)
                     .subscribe();

        } catch (WebClientRequestException e) {
            log.error("Problème lors de connexion avec le Invitation-Service", e);
            throw new MicroserviceAccessFailureException("Invitation");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e);
        }
    }


    private void verifyDataCollaboration(CollaborationCreateRequest collaborationCreateRequest, String context) throws RequiredDataException {
        String object = "de la collaboration";

        if(isNotNullValue(collaborationCreateRequest.getTitre())) {
            log.warn("Titre est obligatoire pour "+context+" "+object);
            throw new RequiredDataException("Titre",context,object);
        }

        if(isNotNullValue(collaborationCreateRequest.getIdProprietaire())) {
            log.warn("Identifiant de propriétaire est obligatoire pour "+context+" "+object);
            throw new RequiredDataException("Identifiant",context,object);
        }

        if(collaborationCreateRequest.getConfidentielle() == null) {
            log.warn("Confidentialité est obligatoire pour "+context+" "+object);
            throw new RequiredDataException("Confidentialité",context,object);
        }
    }

    private Collaboration receiveCollaboration(Long idCollaboration) throws NotFoundDataException {
        Optional<Collaboration> collaboration = collaborationRepository.findById(idCollaboration);

        if(collaboration.isEmpty()) throw new NotFoundDataException("Collaboration",idCollaboration);
        return collaboration.get();
    }

    private void updateOrCreateParticipation(String idParticipant,Collaboration collaboration) {
        Optional<Participation> participation = participationRepository.findByIdParticipantAndCollaboration(idParticipant,collaboration);

        if (participation.isPresent())
        {
            participation.get().setDateParticiaption(new Date());
            participationRepository.save(participation.get());
            return;
        }

        participationRepository.save(
                Participation.builder()
                        .idParticipant(idParticipant)
                        .collaboration(collaboration)
                        .dateParticiaption(new Date())
                        .build()
        );
    }

    private boolean hasJoinedCollaboration(String idMembre, Collaboration collaboration) throws Exception {
        if( !collaboration.getConfidentielle()) {
            updateOrCreateParticipation(idMembre,collaboration);
            return true;
        }
        List<Invitation>  invitationsOfCollaboration = invitationRepository.findByCollaboration(collaboration);

        Optional<Invitation> invitationOfCollaboration = invitationsOfCollaboration.stream()
                .filter(invitation -> Objects.equals(invitation.getIdInvite(), idMembre))
                .findFirst();

        if(invitationOfCollaboration.isPresent()) return false;
        updateOrCreateParticipation(idMembre,collaboration);

        return true;
    }

    @Override
    @Transactional
    public CollaborationResponse createOne(CollaborationCreateRequest collaborationCreateRequest) throws Exception {

        verifyDataCollaboration(collaborationCreateRequest,"l'ajout");

        // vérification le propriétaire (membre) au niveau de base de données
        if(receiveMembreById(collaborationCreateRequest.getIdProprietaire()).isEmpty())
            throw new NotFoundDataException("Proriétaire", collaborationCreateRequest.getIdProprietaire());

        Collaboration collaboration = collaborationMapper.fromReqToModel(collaborationCreateRequest);

        // insertion les valeurs par défaut de colonne
        collaboration.setDateCreationCollaboration(new Date());
        collaboration.setVisible(true);

        // vérifier l'existance de date de départ
        if (isNotNullValue(collaboration.getDateDepart()) )
            collaboration.setDateDepart(collaboration.getDateCreationCollaboration());


        Collaboration createdCollaboration = collaborationRepository.saveAndFlush(collaboration);
        InvitationListRequest  invitationListRequest = new InvitationListRequest();

        invitationListRequest.setIdCollaboration(createdCollaboration.getIdCollaboration());
        collaborationCreateRequest.getIdInvites().forEach(invitationListRequest::addInvite);

        createGuestList(invitationListRequest);

        log.info("Collaboration en ligne est enregistrée : {}", collaboration);

        return collaborationMapper.fromModelToRes(
                createdCollaboration
        );
    }

    @Override
    public Set<CollaborationResponse> getAll() throws NotFoundDataException, MicroserviceAccessFailureException {
        List<Collaboration> collaborations = collaborationRepository.findAll();
        log.info("Collaborations tourvées sont : {}",collaborations);

        Set<CollaborationResponse> collaborationResponseSet = new HashSet<>();

        for(Collaboration collaboration : collaborations)
            collaborationResponseSet.add(
                    getOne(collaboration.getIdCollaboration())
            );
        return collaborationResponseSet;
    }

    @Override
    public CollaborationResponse getOne(Long idCollaboration) throws NotFoundDataException, MicroserviceAccessFailureException {
        Optional<Collaboration> collaboration = Optional.of(receiveCollaboration(idCollaboration));
        log.info("Collaboration tourvée est : {}",collaboration.get());

        Set<String> participationIdSet = participationRepository.findByCollaboration(collaboration.get()).stream().map(Participation::getIdParticipant).collect(Collectors.toSet());

        CollaborationResponse response = collaborationMapper.fromModelToRes(
                collaboration.get()
        );

        for (String participationId : participationIdSet)
            response.getParticipants().add(
                    receiveMembreById(participationId).get()
            );

        return response;
    }

    @Override
    public CollaborationResponse update(Long idCollaboration, CollaborationUpdateRequest collaborationUpdateRequest) throws RequiredDataException, NotFoundDataException {

        Optional<Collaboration> collaborationSearched = Optional.of(receiveCollaboration(idCollaboration));
        log.info("Collaboration tourvée est : {}",collaborationSearched);

        // vérification le titre et la confidentialité
        verifyDataCollaboration(
                CollaborationCreateRequest.builder()
                        .titre(collaborationUpdateRequest.getTitre())
                        .confidentielle(collaborationUpdateRequest.getConfidentielle())
                        .dateDepart(collaborationUpdateRequest.getDateDepart())
                        .idProprietaire("*")
                        .build(),
                "le mise à jour");

        // update les valeurs
        collaborationSearched.get().setTitre(collaborationUpdateRequest.getTitre());
        collaborationSearched.get().setConfidentielle(collaborationUpdateRequest.getConfidentielle());
        collaborationSearched.get().setDateDepart(collaborationUpdateRequest.getDateDepart());

        // vérifier l'existance de date de départ
        if (isNotNullValue(collaborationUpdateRequest.getDateDepart()))
            collaborationSearched.get().setDateDepart(collaborationSearched.get().getDateCreationCollaboration());

        log.info("Collaboration en ligne est modifiée : "+collaborationSearched.get());

        return collaborationMapper.fromModelToRes(
                collaborationRepository.save(collaborationSearched.get())
        );
    }

    private List<String> getNotInvitedAndParticipatedToCollaboration (Collaboration collaboration) {
        // Récupérer les membres invités sur la collaboration
        List<String> invitedMemberIds = invitationRepository
                .findByCollaboration(collaboration).stream()
                .map(Invitation::getIdInvite)
                .toList();

        // Récupérer les membres participés sur la collaboration
        List<String> participatedMemberIds = participationRepository
                .findByCollaboration(collaboration).stream()
                .map(Participation::getIdParticipant)
                .toList();

        // Fusionner les deux listes et éliminer les doublons
        Set<String> uniqueMemberIds = new HashSet<>();
        uniqueMemberIds.addAll(invitedMemberIds);
        uniqueMemberIds.addAll(participatedMemberIds);

        // ajouter l'identifiant du proriétaire de la collaboration
        ArrayList<String> memberIds = new ArrayList<>(uniqueMemberIds);
        memberIds.add(collaboration.getIdProprietaire());

        return memberIds;
    }

    @Override
    public List<MembreResponse> getMembersForJoiningCollaboration(Long idCollaboration) throws NotFoundDataException, MicroserviceAccessFailureException {
        Optional<Collaboration> collaborationSearched = Optional.of(receiveCollaboration(idCollaboration));

        List<MembreResponse> membreResponseList = receiveAllMembres();
        if(receiveAllMembres().isEmpty())
            throw new NotFoundDataException("Membres sont introuvables pour cette instant");

        return membreResponseList.stream()
                .filter( membre ->
                       ! getNotInvitedAndParticipatedToCollaboration(collaborationSearched.get()).contains(membre.getIdMembre())
                )
                .toList();
    }

    @Override
    public Set<CollaborationResponse> searchCollaboration(String collaborationTitle) throws NotFoundDataException, MicroserviceAccessFailureException {

        if(isNotNullValue(collaborationTitle)) throw new NotFoundDataException("Nom de collaboration est obligatoire pour la recherche");
        List<Collaboration> collaborationList = collaborationRepository.findByTitreContainingIgnoreCase(collaborationTitle);

        Set<CollaborationResponse> collaborationResponseSet = new HashSet<>(collaborationList.size());

        for(Collaboration collaboration : collaborationList)
            collaborationResponseSet.add(
                    getOne(collaboration.getIdCollaboration())
            );

        return collaborationResponseSet;
    }

    @Override
    public boolean delete(Long idCollaboration) throws NotFoundDataException {
        Optional<Collaboration> collaboration = Optional.of(receiveCollaboration(idCollaboration));
        log.info("Collaboration d'id est bien supprimée : {}",idCollaboration);
        collaborationRepository.delete(collaboration.get());
        return true;
    }

    @Override
    public CollaborationResponse joindre(Long idCollaboration, JoinCollaborationRequest joinRequest) throws Exception {
        Optional<Collaboration> collaboration = Optional.of(receiveCollaboration(idCollaboration));

        if( hasJoinedCollaboration(
                joinRequest.getIdMembre(),collaboration.get())
        ) return getOne(idCollaboration);

        throw new CollaborationAccessDeniedException(idCollaboration);
    }
}

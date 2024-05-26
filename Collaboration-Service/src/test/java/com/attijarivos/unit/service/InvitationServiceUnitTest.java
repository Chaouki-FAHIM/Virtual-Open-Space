package com.attijarivos.unit.service;

import com.attijarivos.DTO.request.InvitationListRequest;
import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.DTO.response.InvitationResponse;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.ICollaborationTest;
import com.attijarivos.IInvitationTest;
import com.attijarivos.exception.*;
import com.attijarivos.mapper.InvitationMapper;
import com.attijarivos.model.Collaboration;
import com.attijarivos.model.Invitation;
import com.attijarivos.repository.CollaborationRepository;
import com.attijarivos.repository.InvitationRepository;
import com.attijarivos.service.InvitationService;
import com.attijarivos.unit.WebClientTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


public class InvitationServiceUnitTest extends  WebClientTest implements IInvitationTest {

    @Mock
    private CollaborationRepository collaborationRepository;
    @Mock
    private InvitationRepository invitationRepository;
    @Mock
    private InvitationMapper invitationMapper;
    @InjectMocks
    private InvitationService invitationService;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(MembreResponse.class)).thenReturn(Mono.just(new MembreResponse()).flux());
    }

    @Test
    void createNewInvitation() throws RequiredDataException, RededicationInvitationException, NotFoundDataException, NotValidOwnerInviteException, MicroserviceAccessFailureException {

        // Arrange
        Long idCollaboration = 1L;
        InvitationRequest request = InvitationRequest.builder()
                .idInvite(SECOND_MEMBRE_ID)
                .idCollaboration(idCollaboration)
                .build();

        Collaboration collaboration = getCollaboration(idCollaboration);

        Invitation invitation = getInvitation(1L,idCollaboration);

        InvitationResponse expectedResponse = InvitationResponse.builder()
                .idInvitation(1L)
                .idInvite(SECOND_MEMBRE_ID)
                .dateCreationInvitation(invitation.getDateCreationInvitation())
                .collaboration(CollaborationResponse.builder()
                        .idCollaboration(idCollaboration)
                        .titre("Collaboration Test")
                        .build())
                .build();

        when(collaborationRepository.findByIdCollaboration(idCollaboration)).thenReturn(collaboration);
        when(invitationMapper.fromReqToModel(any(InvitationRequest.class))).thenReturn(invitation);
        when(invitationRepository.save(any(Invitation.class))).thenReturn(invitation);
        when(invitationMapper.fromModelToRes(any(Invitation.class))).thenReturn(expectedResponse);

        // Act
        InvitationResponse response = invitationService.createOne(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void createInvitationList() throws RequiredDataException {
        // Arrange
        Long idCollaboration = 1L;
        List<String> inviteIds = List.of(SECOND_MEMBRE_ID, "5574477");

        InvitationListRequest requestList = InvitationListRequest.builder()
                .idCollaboration(idCollaboration)
                .idInvites(inviteIds)
                .build();

        Collaboration collaboration = getCollaboration(idCollaboration);

        when(collaborationRepository.findByIdCollaboration(idCollaboration)).thenReturn(collaboration);
        when(invitationMapper.fromReqToModel(any(InvitationRequest.class))).thenReturn(getInvitation(1L,idCollaboration));
        when(invitationRepository.save(any(Invitation.class))).thenReturn(getInvitation(1L,idCollaboration));

        List<Invitation> invitations = new ArrayList<>();
        List<InvitationResponse> expectedResponses = new ArrayList<>();

        for (int i = 0; i < inviteIds.size(); i++) {
            String inviteId = inviteIds.get(i);
            Invitation invitation = Invitation.builder()
                    .idInvitation((long) i + 1)
                    .idInvite(inviteId)
                    .dateCreationInvitation(new Date())
                    .collaboration(collaboration)
                    .build();
            invitations.add(invitation);

            InvitationResponse expectedResponse = InvitationResponse.builder()
                    .idInvitation((long) i + 1)
                    .idInvite(inviteId)
                    .dateCreationInvitation(invitation.getDateCreationInvitation())
                    .collaboration(CollaborationResponse.builder()
                            .idCollaboration(idCollaboration)
                            .titre("Collaboration Test")
                            .build())
                    .build();
            expectedResponses.add(expectedResponse);

            when(invitationMapper.fromReqToModel(any(InvitationRequest.class))).thenReturn(invitation);
            when(invitationRepository.save(any(Invitation.class))).thenReturn(invitation);
            when(invitationMapper.fromModelToRes(any(Invitation.class))).thenReturn(expectedResponse);

        }

        // Act
        List<InvitationResponse> responseList = invitationService.createInvitationList(requestList);

        // Assert
        assertNotNull(responseList);
        assertEquals(expectedResponses.size(), responseList.size());
    }

    @Test
    void getAllInvitations() throws NotFoundDataException {

        // Arrange
        Invitation invitation1 = getInvitation(1L,1L);
        Invitation invitation2 = getInvitation(2L,1L);

        List<Invitation> invitationList = Arrays.asList(invitation1, invitation2);

        InvitationResponse response1 = invitationMapper.fromModelToRes(invitation1);
        InvitationResponse response2 = invitationMapper.fromModelToRes(invitation2);

        List<InvitationResponse> expectedResponseList = Arrays.asList(response1, response2);

        when(invitationRepository.findAll()).thenReturn(invitationList);
        when(invitationMapper.fromModelToRes(invitation1)).thenReturn(response1);
        when(invitationMapper.fromModelToRes(invitation2)).thenReturn(response2);

        // Act
        Set<InvitationResponse> actualResponseList = invitationService.getAll();

        // Assert
        assertNotNull(actualResponseList);
        assertEquals(expectedResponseList.size(), actualResponseList.size());
        assertEquals(expectedResponseList, actualResponseList);
    }

    @Test
    void getInvitationById() throws NotFoundDataException {
        // Arrange
        Long idInvitation = 1L;
        Collaboration collaboration = getCollaboration(1L);
        Invitation invitation = getInvitation(idInvitation,1L);

        InvitationResponse expectedResponse = InvitationResponse.builder()
                .idInvitation(invitation.getIdInvitation())
                .idInvite(invitation.getIdInvite())
                .dateCreationInvitation(invitation.getDateCreationInvitation())
                .collaboration(CollaborationResponse.builder()
                        .idCollaboration(collaboration.getIdCollaboration())
                        .titre("Collaboration Test")
                        .build())
                .build();

        when(invitationRepository.findById(idInvitation)).thenReturn(Optional.of(invitation));
        when(invitationMapper.fromModelToRes(invitation)).thenReturn(expectedResponse);

        // Act
        InvitationResponse actualResponse = invitationService.getOne(idInvitation);

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getNonExistingInvitationById() {
        // Arrange
        Long idInvitation = 1L;

        when(invitationRepository.findById(idInvitation)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundDataException.class, () -> {
            invitationService.getOne(idInvitation);
        });
    }

    @Test
    void deleteInvitation() throws NotFoundDataException {
        // Arrange
        Long idInvitation = 1L;
        Invitation invitation = Invitation.builder()
                .idInvitation(idInvitation)
                .idInvite(SECOND_MEMBRE_ID)
                .dateCreationInvitation(new Date())
                .collaboration(getCollaboration(1L))
                .build();

        when(invitationRepository.findById(idInvitation)).thenReturn(Optional.of(invitation));
        doNothing().when(invitationRepository).delete(invitation);

        // Act
        boolean result = invitationService.delete(idInvitation);

        // Assert
        assertTrue(result);
    }
}

package com.attijarivos.unit.service;

import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.ICollaborationTest;
import com.attijarivos.configuration.WebClientConfig;
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
import com.attijarivos.service.CollaborationService;
import com.attijarivos.unit.WebClientTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.Collectors;

public class CollaborationServiceUnitTest extends WebClientTest implements ICollaborationTest {

    @Mock
    private CollaborationRepository collaborationRepository;
    @Mock
    private CollaborationMapper collaborationMapper;
    @Mock
    private InvitationRepository invitationRepository;
    @Mock
    private ParticipationRepository participationRepository;
    @InjectMocks
    private CollaborationService collaborationService;


    @Test
    public void testWebClientInteraction() {

        WebClient realWebClient = WebClient.create();

        String response = realWebClient.get()
                .uri(WebClientConfig.MEMBRE_SERVICE_URL)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        assertNotNull(response);
    }

    @Test
    void createCollaboration() throws RequiredDataException, NotFoundDataException, MicroserviceAccessFailureException {

        // Arrange
        CollaborationRequest request = getCollaborationRequest();
        Collaboration collaboration = new Collaboration();

        CollaborationResponse expectedResponse = new CollaborationResponse();

        when(collaborationMapper.fromReqToModel(any(CollaborationRequest.class))).thenReturn(collaboration);
        when(collaborationRepository.save(any(Collaboration.class))).thenReturn(collaboration);
        when(collaborationMapper.fromModelToRes(any(Collaboration.class))).thenReturn(expectedResponse);

        // Act
        CollaborationResponse response = collaborationService.createOne(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void getOneCollaboration() throws NotFoundDataException, MicroserviceAccessFailureException {

        // Arrange
        Long idCollaboration = 1L;
        Collaboration collaboration = getCollaboration(idCollaboration);

        CollaborationResponse expectedResponse = new CollaborationResponse();
        expectedResponse.setIdCollaboration(idCollaboration);
        expectedResponse.setTitre("Collaboration Test");

        when(collaborationRepository.findById(idCollaboration)).thenReturn(Optional.of(collaboration));
        when(collaborationMapper.fromModelToRes(collaboration)).thenReturn(expectedResponse);

        // Act
        CollaborationResponse response = collaborationService.getOne(idCollaboration);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void getAllCollaborations() throws NotFoundDataException, MicroserviceAccessFailureException {

        // Arrange
        Collaboration collaboration1 = getCollaboration(1L);
        Collaboration collaboration2 = getCollaboration(2L);

        Set<Collaboration> collaborations = new HashSet<>();
        collaborations.add(collaboration1);
        collaborations.add(collaboration2);

        CollaborationResponse response1 = collaborationMapper.fromModelToRes(collaboration1);
        CollaborationResponse response2 = collaborationMapper.fromModelToRes(collaboration2);

        Set<CollaborationResponse> expectedResponses = new HashSet<>();
        expectedResponses.add(response1);
        expectedResponses.add(response2);

        when(collaborationRepository.findAll()).thenReturn((List<Collaboration>) collaborations);
        when(collaborationMapper.fromModelToRes(collaboration1)).thenReturn(response1);
        when(collaborationMapper.fromModelToRes(collaboration2)).thenReturn(response2);

        // Act
        Set<CollaborationResponse> responses = collaborationService.getAll();

        // Assert
        assertNotNull(responses);
        assertEquals(expectedResponses.size(), responses.size());
        assertEquals(expectedResponses, responses);
    }

    @Test
    void updateCollaboration() throws RequiredDataException, NotFoundDataException {

        // Arrange
        long idCollaboration = 1L;
        CollaborationUpdateRequest updateRequest = getCollaborationUpdateRequest();
        updateRequest.setTitre("Updated Collaboration Test");

        Collaboration existingCollaboration = getCollaboration(idCollaboration);
        existingCollaboration.setTitre("Old Collaboration Test");


        Collaboration updatedCollaboration = getCollaboration(idCollaboration);
        updatedCollaboration.setTitre("Updated Collaboration Test");

        CollaborationResponse expectedResponse = new CollaborationResponse();
        expectedResponse.setIdCollaboration(idCollaboration);
        expectedResponse.setTitre("Updated Collaboration Test");

        when(collaborationRepository.findById(idCollaboration)).thenReturn(Optional.of(existingCollaboration));
        when(collaborationRepository.save(any(Collaboration.class))).thenReturn(updatedCollaboration);
        when(collaborationMapper.fromModelToRes(any(Collaboration.class))).thenReturn(expectedResponse);

        // Act
        CollaborationResponse response = collaborationService.update(idCollaboration, updateRequest);

        assertNotNull(collaborationMapper.fromModelToRes(updatedCollaboration));

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
    }

    @Test
    void deleteCollaboration() throws NotFoundDataException {
        // Arrange
        Long idCollaboration = 1L;
        Collaboration collaboration = getCollaboration(idCollaboration);

        boolean expectedResponse = true;

        when(collaborationRepository.findById(idCollaboration)).thenReturn(Optional.of(collaboration));

        // Act
        boolean response = collaborationService.delete(idCollaboration);

        // Assert
        verify(collaborationRepository, times(1)).findById(idCollaboration);
        verify(collaborationRepository, times(1)).delete(collaboration);
        assertEquals(expectedResponse, response);
    }

    @Test
    void getNonInvitedMembers() throws NotFoundDataException, MicroserviceAccessFailureException {
        // Arrange
        long idCollaboration = 1L;
        Collaboration existingCollaboration = getCollaboration(idCollaboration);

        List<MembreResponse> allMembres = Arrays.asList(
                MembreResponse.builder().idMembre("1").nomMembre("FAHIM 1").prenom("Chaouki 1").statutCollaboration(new Random().nextBoolean()).build(),
                MembreResponse.builder().idMembre("2").nomMembre("FAHIM 2").prenom("Chaouki 2").statutCollaboration(true).build(),
                MembreResponse.builder().idMembre("3").nomMembre("FAHIM 3").prenom("Chaouki 3").statutCollaboration(true).build(),
                MembreResponse.builder().idMembre("4").nomMembre("FAHIM 4").prenom("Chaouki 4").statutCollaboration(true).build()
        );

        List<String> invitedMemberIds = Arrays.asList("1");
        List<String> participatedMemberIds = Collections.singletonList("2");

        when(collaborationRepository.findById(idCollaboration)).thenReturn(Optional.of(existingCollaboration));
        when(webClient.get().uri(WebClientConfig.MEMBRE_SERVICE_URL).retrieve().bodyToFlux(MembreResponse.class))
                .thenReturn(Flux.fromIterable(allMembres));
        when(invitationRepository.findByCollaboration(existingCollaboration))
                .thenReturn(invitedMemberIds.stream().map(id -> Invitation.builder().idInvite(id).collaboration(existingCollaboration).build()).toList());
        when(participationRepository.findByCollaboration(existingCollaboration))
                .thenReturn(participatedMemberIds.stream().map(id -> Participation.builder().idParticipant(id).collaboration(existingCollaboration).build()).collect(Collectors.toSet()));

        // Act
        List<MembreResponse> nonInvitedMembers = collaborationService.getMembersForJoiningCollaboration(idCollaboration);

        // Assert
        assertNotNull(nonInvitedMembers);
        assertEquals(2, nonInvitedMembers.size());
        assertEquals("FAHIM 3", nonInvitedMembers.get(0).getNomMembre());
    }

    @Test
    void joinCollaboration() throws Exception {
        // Arrange
        long idCollaboration = 1L;
        String membreId = "2";
        Collaboration existingCollaboration = getCollaboration(idCollaboration);

        JoinCollaborationRequest joinRequest = new JoinCollaborationRequest(membreId);

        CollaborationResponse expectedResponse = new CollaborationResponse();
        expectedResponse.setIdCollaboration(idCollaboration);

        when(collaborationRepository.findById(idCollaboration)).thenReturn(Optional.of(existingCollaboration));
        when(participationRepository.findByIdParticipantAndCollaboration(membreId, existingCollaboration))
                .thenReturn(Optional.of(Participation.builder().idParticipant(membreId).collaboration(existingCollaboration).build()));
        when(collaborationMapper.fromModelToRes(existingCollaboration)).thenReturn(expectedResponse);

        // Act
        CollaborationResponse response = collaborationService.joindre(idCollaboration, joinRequest);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);
        assertNotNull(participationRepository.findByIdParticipantAndCollaboration(membreId, existingCollaboration).get().getDateParticiaption());
    }

}

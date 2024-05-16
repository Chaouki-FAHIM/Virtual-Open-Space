package com.attijarivos.unit.service;

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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void getAllInvitations() {

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
        List<InvitationResponse> actualResponseList = invitationService.getAll();

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

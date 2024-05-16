package com.attijarivos.unit.service;

import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.DTO.response.InvitationResponse;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.ICollaborationTest;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


public class InvitationServiceUnitTest extends  WebClientTest implements ICollaborationTest {

    @Mock
    private CollaborationRepository collaborationRepository;
    @Mock
    private InvitationRepository invitationRepository;
    @Mock
    private InvitationMapper invitationMapper;
    @InjectMocks
    private InvitationService invitationService;


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
        assertEquals(true, result);
    }
}

package com.attijarivos.unit.controller;

import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.DTO.response.InvitationResponse;
import com.attijarivos.IInvitationTest;
import com.attijarivos.exception.*;
import com.attijarivos.service.IInvitationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InvitationControllerUnitTest implements IInvitationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IInvitationService<InvitationRequest, InvitationResponse, Long> invitationService;
    @Autowired
    private ObjectMapper objectMapper;

    private final String URI = "/invitations";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createInvitationSuccess() throws Exception {
        // Arrange
        InvitationRequest request = getInvitationRequest();
        InvitationResponse response = getInvitationResponse(1L, request);

        when(invitationService.createOne(any(InvitationRequest.class))).thenReturn(response);

        // Act
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Assert
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idInvitation").value(response.getIdInvitation()))
                .andExpect(jsonPath("$.idInvite").value(response.getIdInvite()))
                .andExpect(jsonPath("$.collaboration.idCollaboration").value(response.getCollaboration().getIdCollaboration()));

        // Verify
        verify(invitationService, times(1)).createOne(any(InvitationRequest.class));
    }

    @Test
    public void createInvitationWithMissingIdInvite() throws Exception {
        // Arrange
        InvitationRequest request = getInvitationRequest();
        request.setIdInvite(null);

        doThrow(new RequiredDataException("Identifiant d'invité", "la création", "de l'invitation"))
                .when(invitationService).createOne(any(InvitationRequest.class));

        // Act
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Identifiant d'invité est obligatoire pour la création de l'invitation"));

        // Verify
        verify(invitationService, times(1)).createOne(any(InvitationRequest.class));
    }

    @Test
    public void createInvitationWithMissingIdCollaboration() throws Exception {
        // Arrange
        InvitationRequest request = getInvitationRequest();
        request.setIdCollaboration(null);

        doThrow(new RequiredDataException("Identifiant de la collaboration", "la création", "de l'invitation"))
                .when(invitationService).createOne(any(InvitationRequest.class));

        // Act
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Identifiant de la collaboration est obligatoire pour la création de l'invitation"));

        // Verify
        verify(invitationService, times(1)).createOne(any(InvitationRequest.class));
    }

    @Test
    public void createInvitationWithNotFoundInvite() throws Exception {
        // Arrange
        InvitationRequest request = getInvitationRequest();

        doThrow(new NotFoundDataException("Invité", request.getIdInvite()))
                .when(invitationService).createOne(any(InvitationRequest.class));

        // Act
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Invité avec l'id " + request.getIdInvite() + " est introuvable !!"));

        // Verify
        verify(invitationService, times(1)).createOne(any(InvitationRequest.class));
    }

    @Test
    public void createInvitationWithRededicationException() throws Exception {
        // Arrange
        InvitationRequest request = getInvitationRequest();

        doThrow(new RededicationInvitationException())
                .when(invitationService).createOne(any(InvitationRequest.class));

        // Act
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Invitation a déjà été envoyée pour cette collaboration en ligne"));

        // Verify
        verify(invitationService, times(1)).createOne(any(InvitationRequest.class));
    }

    @Test
    public void createInvitationWithNotValidOwnerInviteException() throws Exception {
        // Arrange
        String idInvite = "123456789";
        InvitationRequest request = getInvitationRequest();
        request.setIdInvite(idInvite);

        doThrow(new NotValidOwnerInviteException(idInvite))
                .when(invitationService).createOne(any(InvitationRequest.class));

        // Act
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("L'identifiant de membre invité " + idInvite + " est un identifiant de propriétaire")));

        // Verify
        verify(invitationService, times(1)).createOne(any(InvitationRequest.class));
    }

    @Test
    public void getInvitationByIdValid() throws Exception {
        // Arrange
        Long idInvitation = 1L;
        InvitationRequest request = getInvitationRequest();
        InvitationResponse response = getInvitationResponse(idInvitation, request);

        when(invitationService.getOne(idInvitation)).thenReturn(response);

        // Act
        mockMvc.perform(get(URI + "/" + idInvitation)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idInvitation").value(response.getIdInvitation()))
                .andExpect(jsonPath("$.idInvite").value(response.getIdInvite()))
                .andExpect(jsonPath("$.collaboration.idCollaboration").value(response.getCollaboration().getIdCollaboration()));

        // Verify
        verify(invitationService, times(1)).getOne(idInvitation);
    }

    @Test
    public void getInvitationByIdNotFound() throws Exception {
        // Arrange
        Long idInvitation = 1L;

        doThrow(new NotFoundDataException("Invitation", idInvitation))
                .when(invitationService).getOne(idInvitation);

        // Act
        mockMvc.perform(get(URI + "/" + idInvitation)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Invitation avec l'id " + idInvitation + " est introuvable !!"));

        // Verify
        verify(invitationService, times(1)).getOne(idInvitation);
    }

    @Test
    public void getAllInvitationsSuccess() throws Exception {
        // Arrange
        List<InvitationResponse> invitations = List.of(
                getInvitationResponse(1L, getInvitationRequest()),
                getInvitationResponse(2L, getInvitationRequest())
        );

        when(invitationService.getAll()).thenReturn(invitations);

        // Act
        mockMvc.perform(get(URI)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idInvitation").value(invitations.get(0).getIdInvitation()))
                .andExpect(jsonPath("$[0].idInvite").value(invitations.get(0).getIdInvite()))
                .andExpect(jsonPath("$[0].collaboration.idCollaboration").value(invitations.get(0).getCollaboration().getIdCollaboration()))
                .andExpect(jsonPath("$[1].idInvitation").value(invitations.get(1).getIdInvitation()))
                .andExpect(jsonPath("$[1].idInvite").value(invitations.get(1).getIdInvite()))
                .andExpect(jsonPath("$[1].collaboration.idCollaboration").value(invitations.get(1).getCollaboration().getIdCollaboration()));

        // Verify
        verify(invitationService, times(1)).getAll();
    }

    @Test
    public void getAllInvitationsEmptyList() throws Exception {
        // Arrange
        doThrow(new NotFoundDataException("Liste des invitations est vide !!"))
                .when(invitationService).getAll();

        // Act
        mockMvc.perform(get(URI)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isNoContent())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Liste des invitations est vide !!"));

        // Verify
        verify(invitationService, times(1)).getAll();
    }



}

package com.attijarivos.unit.controller;

import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.ICollaborationTest;
import com.attijarivos.exception.*;
import com.attijarivos.service.ICollaborationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CollaborationControllerUnitTest implements ICollaborationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ICollaborationService<CollaborationRequest, CollaborationResponse, Long> collaborationService;
    @Autowired
    private ObjectMapper objectMapper;

    private final String URI = "/collaborations";


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCollaborationSuccess() throws Exception {
        CollaborationRequest request = getCollaborationRequest();
        CollaborationResponse response = getCollaborationResponse(1L,request);

        when(collaborationService.createOne(any(CollaborationRequest.class))).thenReturn(response);

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idCollaboration").value(response.getIdCollaboration()))
                .andExpect(jsonPath("$.idProprietaire").value(response.getIdProprietaire()))
//                .andExpect(jsonPath("$.dateDepart").value(dateFormat.format(response.getDateDepart())))
//                .andExpect(jsonPath("$.dateCreationCollaboration").value(response.getDateCreationCollaboration())) // Modification ici
                .andExpect(jsonPath("$.titre").value(response.getTitre()))
                .andExpect(jsonPath("$.confidentielle").value(response.getConfidentielle()));
    }

    @Test
    void createCollaborationWithRequiredTitle() throws Exception {
        CollaborationRequest request = getCollaborationRequest();
        request.setTitre(null);

        when(collaborationService.createOne(any(CollaborationRequest.class)))
                .thenThrow(new RequiredDataException("Titre", "l'ajout", "de la collaboration"));

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Titre est obligatoire pour l'ajout de la collaboration"));  // Vérifier le message d'erreur
    }

    @Test
    void createCollaborationWithRequiredConfidentielle() throws Exception {
        CollaborationRequest request = getCollaborationRequest();
        request.setConfidentielle(null);

        when(collaborationService.createOne(any(CollaborationRequest.class)))
                .thenThrow(new RequiredDataException("Confidentialité", "l'ajout", "de la collaboration"));

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Confidentialité est obligatoire pour l'ajout de la collaboration"));
    }

    @Test
    void createCollaborationWithRequiredOwnerID() throws Exception {
        CollaborationRequest request = getCollaborationRequest();
        request.setIdProprietaire(null);

        when(collaborationService.createOne(any(CollaborationRequest.class)))
                .thenThrow(new RequiredDataException("Identifiant de propriétaire", "l'ajout", "de la collaboration"));

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())  // Vérifier que le statut est BAD_REQUEST
                .andExpect(content().contentType("text/plain;charset=UTF-8"))  // Vérifier le type de contenu
                .andExpect(content().string("Identifiant de propriétaire est obligatoire pour l'ajout de la collaboration"));  // Vérifier le message d'erreur
    }

    @Test
    void getAllCollaborationsSuccess() throws Exception {

        List<CollaborationResponse> collaborations = List.of(
                getCollaborationResponse(1L,getCollaborationRequest()),
                getCollaborationResponse(2L,getCollaborationRequest())
        );

        when(collaborationService.getAll()).thenReturn(collaborations);

        mockMvc.perform(get(URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idCollaboration").value(collaborations.get(0).getIdCollaboration()))
                .andExpect(jsonPath("$[0].titre").value(collaborations.get(0).getTitre()))
                .andExpect(jsonPath("$[0].confidentielle").value(collaborations.get(0).getConfidentielle()))
                .andExpect(jsonPath("$[0].idProprietaire").value(collaborations.get(0).getIdProprietaire()))
                .andExpect(jsonPath("$[1].idCollaboration").value(collaborations.get(1).getIdCollaboration()))
                .andExpect(jsonPath("$[1].titre").value(collaborations.get(1).getTitre()))
                .andExpect(jsonPath("$[1].confidentielle").value(collaborations.get(1).getConfidentielle()))
                .andExpect(jsonPath("$[1].idProprietaire").value(collaborations.get(1).getIdProprietaire()));
    }

    @Test
    void getOneCollaborationSuccess() throws Exception {
        Long idCollaboration = 1L;
        CollaborationResponse response = getCollaborationResponse(idCollaboration,getCollaborationRequest());

        when(collaborationService.getOne(idCollaboration)).thenReturn(response);

        mockMvc.perform(get(URI + "/" + idCollaboration)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idCollaboration").value(response.getIdCollaboration()))
                .andExpect(jsonPath("$.titre").value(response.getTitre()))
                .andExpect(jsonPath("$.confidentielle").value(response.getConfidentielle()))
                .andExpect(jsonPath("$.idProprietaire").value(response.getIdProprietaire()));
    }

    @Test
    public void getOneCollaborationNotFound() throws Exception {
        // Arrange
        Long idCollaboration = 1L;

        doThrow(new NotFoundDataException("Collaboration", idCollaboration))
                .when(collaborationService).getOne(idCollaboration);

        // Act
        mockMvc.perform(get(URI + "/" + idCollaboration)
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Collaboration avec l'id " + idCollaboration + " est introuvable !!"));

        // Verify
        verify(collaborationService, times(1)).getOne(idCollaboration);
    }

    @Test
    public void deleteCollaborationByIdSuccess() throws Exception {
        Long idCollaboration = 1L;
        CollaborationResponse response = getCollaborationResponse(idCollaboration,getCollaborationRequest());

        when(collaborationService.getOne(idCollaboration)).thenReturn(response);

        mockMvc.perform(delete(URI + "/" + idCollaboration))
                .andExpect(status().isOk());

        verify(collaborationService, times(1)).delete(idCollaboration);
    }

    @Test
    void deleteCollaborationByIdWithNotFoundData() throws Exception {
        Long idCollaboration = 1L;

        doThrow(new NotFoundDataException("Collaboration", idCollaboration)).when(collaborationService).delete(idCollaboration);

        mockMvc.perform(delete(URI + "/" + idCollaboration)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Collaboration avec l'id 1 est introuvable !!"));
    }

    @Test
    public void updateCollaborationSuccess() throws Exception {
        // Arrange
        Long idCollaboration = 1L;
        CollaborationUpdateRequest updateRequest = getCollaborationUpdateRequest();
        CollaborationResponse response = getCollaborationResponse(idCollaboration, getCollaborationRequest());

        when(collaborationService.update(eq(idCollaboration), any(CollaborationUpdateRequest.class))).thenReturn(response);

        // Act
        mockMvc.perform(put(URI + "/" + idCollaboration)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idCollaboration").value(response.getIdCollaboration()))
                .andExpect(jsonPath("$.titre").value(response.getTitre()))
                .andExpect(jsonPath("$.confidentielle").value(response.getConfidentielle()))
                .andExpect(jsonPath("$.idProprietaire").value(response.getIdProprietaire()));

        // Assert
        verify(collaborationService, times(1)).update(eq(idCollaboration), any(CollaborationUpdateRequest.class));
    }

    @Test
    public void updateCollaborationWithRequiredTitle() throws Exception {
        // Arrange
        Long idCollaboration = 1L;
        CollaborationUpdateRequest updateRequest = getCollaborationUpdateRequest();
        updateRequest.setTitre(null);

        doThrow(new RequiredDataException("Titre", "la mise à jour", "de la collaboration"))
                .when(collaborationService).update(eq(idCollaboration), any(CollaborationUpdateRequest.class));

        // Act
        mockMvc.perform(put(URI + "/" + idCollaboration)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Titre est obligatoire pour la mise à jour de la collaboration"));

        // Verify
        verify(collaborationService, times(1)).update(eq(idCollaboration), any(CollaborationUpdateRequest.class));
    }

    @Test
    public void updateCollaborationWithRequiredConfidentielle() throws Exception {
        // Arrange
        Long idCollaboration = 1L;
        CollaborationUpdateRequest updateRequest = getCollaborationUpdateRequest();
        updateRequest.setConfidentielle(null);

        doThrow(new RequiredDataException("Confidentialité", "la mise à jour", "de la collaboration"))
                .when(collaborationService).update(eq(idCollaboration), any(CollaborationUpdateRequest.class));

        // Act
        mockMvc.perform(put(URI + "/" + idCollaboration)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Confidentialité est obligatoire pour la mise à jour de la collaboration"));

        // Verify
        verify(collaborationService, times(1)).update(eq(idCollaboration), any(CollaborationUpdateRequest.class));
    }

    @Test
    public void joinCollaborationSuccess() throws Exception {
        // Arrange
        Long idCollaboration = 1L;
        JoinCollaborationRequest joinRequest = JoinCollaborationRequest.builder()
                .idMembre("123")
                .build();
        CollaborationResponse response = getCollaborationResponse(idCollaboration, getCollaborationRequest());

        when(collaborationService.joindre(eq(idCollaboration), any(JoinCollaborationRequest.class))).thenReturn(response);

        // Act
        mockMvc.perform(patch(URI + "/" + idCollaboration + "/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                // Assert
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idCollaboration").value(response.getIdCollaboration()))
                .andExpect(jsonPath("$.titre").value(response.getTitre()))
                .andExpect(jsonPath("$.confidentielle").value(response.getConfidentielle()))
                .andExpect(jsonPath("$.idProprietaire").value(response.getIdProprietaire()));

        // Verify
        verify(collaborationService, times(1)).joindre(eq(idCollaboration), any(JoinCollaborationRequest.class));
    }

    @Test
    public void joinCollaborationWithRequiredDataException() throws Exception {
        // Arrange
        Long idCollaboration = 1L;
        JoinCollaborationRequest joinRequest = JoinCollaborationRequest.builder()
                .idMembre(null) // idMembre manquant
                .build();

        doThrow(new RequiredDataException("idMembre", "la jointure", "de la collaboration"))
                .when(collaborationService).joindre(eq(idCollaboration), any(JoinCollaborationRequest.class));

        // Act
        mockMvc.perform(patch(URI + "/" + idCollaboration + "/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("idMembre est obligatoire pour la jointure de la collaboration"));

        // Verify
        verify(collaborationService, times(1)).joindre(eq(idCollaboration), any(JoinCollaborationRequest.class));
    }

    @Test
    public void joinCollaborationNotFound() throws Exception {
        // Arrange
        Long idCollaboration = 1L;
        JoinCollaborationRequest joinRequest = JoinCollaborationRequest.builder()
                .idMembre("123")
                .build();

        doThrow(new NotFoundDataException("Collaboration", idCollaboration))
                .when(collaborationService).joindre(eq(idCollaboration), any(JoinCollaborationRequest.class));

        // Act
        mockMvc.perform(patch(URI + "/" + idCollaboration + "/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Collaboration avec l'id " + idCollaboration + " est introuvable !!"));

        // Verify
        verify(collaborationService, times(1)).joindre(eq(idCollaboration), any(JoinCollaborationRequest.class));
    }

    @Test
    public void joinCollaborationAccessDenied() throws Exception {
        // Arrange
        Long idCollaboration = 1L;
        JoinCollaborationRequest joinRequest = JoinCollaborationRequest.builder()
                .idMembre("123")
                .build();

        doThrow(new CollaborationAccessDeniedException(idCollaboration))
                .when(collaborationService).joindre(eq(idCollaboration), any(JoinCollaborationRequest.class));

        // Act
        mockMvc.perform(patch(URI + "/" + idCollaboration + "/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                // Assert
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Autorisation d'acces à la collaboration confidentielle d'id " + idCollaboration));

        // Verify
        verify(collaborationService, times(1)).joindre(eq(idCollaboration), any(JoinCollaborationRequest.class));
    }

    @Test
    public void getMembersForJoiningCollaborationSuccess() throws Exception {
        // Arrange
        Long idCollaboration = 1L;
        List<MembreResponse> membres = List.of(
                MembreResponse.builder().idMembre("123").nomMembre("Choauki 1").build(),
                MembreResponse.builder().idMembre("282").nomMembre("Choauki 2").build()
        );

        when(collaborationService.getMembersForJoiningCollaboration(idCollaboration)).thenReturn(membres);

        // Act
        mockMvc.perform(get(URI + "/" + idCollaboration + "/uninvited-members")
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idMembre").value(membres.get(0).getIdMembre()))
                .andExpect(jsonPath("$[0].nomMembre").value(membres.get(0).getNomMembre()))
                .andExpect(jsonPath("$[1].idMembre").value(membres.get(1).getIdMembre()))
                .andExpect(jsonPath("$[1].nomMembre").value(membres.get(1).getNomMembre()));

        // Verify
        verify(collaborationService, times(1)).getMembersForJoiningCollaboration(idCollaboration);
    }

    @Test
    public void getMembersForJoiningCollaborationNotFound() throws Exception {
        // Arrange
        Long idCollaboration = 1L;

        doThrow(new NotFoundDataException("Collaboration", idCollaboration))
                .when(collaborationService).getMembersForJoiningCollaboration(idCollaboration);

        // Act
        mockMvc.perform(get(URI + "/" + idCollaboration + "/uninvited-members")
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Collaboration avec l'id " + idCollaboration + " est introuvable !!"));

        // Verify
        verify(collaborationService, times(1)).getMembersForJoiningCollaboration(idCollaboration);
    }

    @Test
    public void getMembersForJoiningCollaborationMicroserviceAccessFailure() throws Exception {
        // Arrange
        Long idCollaboration = 1L;

        doThrow(new MicroserviceAccessFailureException("Membre"))
                .when(collaborationService).getMembersForJoiningCollaboration(idCollaboration);

        // Act
        mockMvc.perform(get(URI + "/" + idCollaboration + "/uninvited-members")
                        .contentType(MediaType.APPLICATION_JSON))
                // Assert
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Problème lors de connexion avec le Micro-Service Membre"));

        // Verify
        verify(collaborationService, times(1)).getMembersForJoiningCollaboration(idCollaboration);
    }

}

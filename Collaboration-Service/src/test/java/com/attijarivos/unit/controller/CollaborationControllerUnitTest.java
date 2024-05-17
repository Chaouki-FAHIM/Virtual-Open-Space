package com.attijarivos.unit.controller;

import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.ICollaborationTest;
import com.attijarivos.exception.*;
import com.attijarivos.service.ICollaborationService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+00:00");


    private final String URI = "/collaborations";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCollaborationWithValidData() throws Exception {
        CollaborationRequest request = getCollaborationRequest();
        CollaborationResponse response = CollaborationResponse.builder()
                .idCollaboration(1L)
                .idProprietaire(request.getIdProprietaire())
                .dateCreationCollaboration(new Date())
                .dateDepart(request.getDateDepart())
                .titre(request.getTitre())
                .confidentielle(request.getConfidentielle())
                .build();

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
    void createCollaborationWithRequiredIdProprietaire() throws Exception {
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
    void getAllCollaborations() throws Exception {

        CollaborationRequest request1 = getCollaborationRequest();
        request1.setTitre("Collaboration Test 1");
        CollaborationRequest request2 = getCollaborationRequest();
        request2.setTitre("Collaboration Test 2");

        List<CollaborationResponse> collaborations = List.of(
                CollaborationResponse.builder()
                        .idCollaboration(1L)
                        .idProprietaire(request1.getIdProprietaire())
                        .dateCreationCollaboration(new Date())
                        .dateDepart(request1.getDateDepart())
                        .titre(request1.getTitre())
                        .confidentielle(request1.getConfidentielle())
                        .build(),
                CollaborationResponse.builder()
                        .idCollaboration(1L)
                        .idProprietaire(request2.getIdProprietaire())
                        .dateCreationCollaboration(new Date())
                        .dateDepart(request2.getDateDepart())
                        .titre(request2.getTitre())
                        .confidentielle(request2.getConfidentielle())
                        .build()
        );

        when(collaborationService.getAll()).thenReturn(collaborations);

        mockMvc.perform(get(URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Vérifier que le statut est OK
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
    void getOneCollaboration() throws Exception {
        Long idCollaboration = 1L;
        CollaborationRequest request = getCollaborationRequest();
        CollaborationResponse response = CollaborationResponse.builder()
                .idCollaboration(idCollaboration)
                .idProprietaire(request.getIdProprietaire())
                .dateCreationCollaboration(new Date())
                .dateDepart(request.getDateDepart())
                .titre(request.getTitre())
                .confidentielle(request.getConfidentielle())
                .build();

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
    void deleteCollaborationByIdWithFoundData() throws Exception {
        Long idCollaboration = 1L;


        doNothing().doThrow(new RuntimeException()).when(collaborationService).delete(idCollaboration);

        mockMvc.perform(delete(URI + "/" + idCollaboration)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Vérifier que le statut est OK
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("Bonne Suppression de la collaboration en ligne d'id : " + idCollaboration));  // Vérifier le message de succès
    }




}

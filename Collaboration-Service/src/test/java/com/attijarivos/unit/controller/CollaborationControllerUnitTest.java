package com.attijarivos.unit.controller;

import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.ICollaborationTest;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}

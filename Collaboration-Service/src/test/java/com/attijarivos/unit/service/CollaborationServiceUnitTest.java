package com.attijarivos.unit.service;

import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.DataTest;
import com.attijarivos.configuration.WebClientConfig;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.mapper.CollaborationMapper;
import com.attijarivos.model.Collaboration;
import com.attijarivos.repository.CollaborationRepository;
import com.attijarivos.service.CollaborationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.*;

public class CollaborationServiceUnitTest implements DataTest {

    @Mock
    private CollaborationRepository collaborationRepository;
    @Mock
    private CollaborationMapper collaborationMapper;
    @Mock
    private WebClient webClient;
    @InjectMocks
    private CollaborationService collaborationService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock WebClient behavior
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(String.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Mocking a valid MembreResponse
        MembreResponse membreResponse = new MembreResponse();
        membreResponse.setId(FIRST_MEMBRE_ID);

        when(responseSpec.bodyToMono(MembreResponse.class)).thenReturn(Mono.just(membreResponse));
    }

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
    void createCollaborationWithValidData() throws RequiredDataException, NotFoundDataException, MicroserviceAccessFailureException {

        // Arrange
        CollaborationRequest request = CollaborationRequest.builder()
                .titre("Collaboration Test")
                .confidentielle(new Random().nextBoolean())
                .dateDepart(new Date())
                .idProprietaire(FIRST_MEMBRE_ID)
                .build();
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
    void getOneCollaboration() throws NotFoundDataException {

        // Arrange
        Long idCollaboration = 1L;
        Collaboration collaboration = Collaboration.builder()
                .idCollaboration(idCollaboration)
                .titre("Collaboration Test")
                .confidentielle(new Random().nextBoolean())
                .dateDepart(new Date())
                .idProprietaire(FIRST_MEMBRE_ID)
                .build();

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
    void getAllCollaborations() {
        // Arrange
        Collaboration collaboration1 = Collaboration.builder()
                .idCollaboration(1L)
                .titre("Collaboration Test 1")
                .confidentielle(new Random().nextBoolean())
                .dateDepart(new Date())
                .idProprietaire(FIRST_MEMBRE_ID)
                .build();

        Collaboration collaboration2 = Collaboration.builder()
                .idCollaboration(2L)
                .titre("Collaboration Test 2")
                .confidentielle(new Random().nextBoolean())
                .dateDepart(new Date())
                .idProprietaire(FIRST_MEMBRE_ID)
                .build();

        List<Collaboration> collaborations = Arrays.asList(collaboration1, collaboration2);

        CollaborationResponse response1 = new CollaborationResponse();
        response1.setIdCollaboration(1L);
        response1.setTitre("Collaboration Test 1");

        CollaborationResponse response2 = new CollaborationResponse();
        response2.setIdCollaboration(2L);
        response2.setTitre("Collaboration Test 2");

        List<CollaborationResponse> expectedResponses = Arrays.asList(response1, response2);

        when(collaborationRepository.findAll()).thenReturn(collaborations);
        when(collaborationMapper.fromModelToRes(collaboration1)).thenReturn(response1);
        when(collaborationMapper.fromModelToRes(collaboration2)).thenReturn(response2);

        // Act
        List<CollaborationResponse> responses = collaborationService.getAll();

        // Assert
        assertNotNull(responses);
        assertEquals(expectedResponses.size(), responses.size());
        assertEquals(expectedResponses, responses);
    }
}

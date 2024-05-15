package com.attijarivos.unit;

import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.MembreIdDataTest;
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

import java.util.Date;
import java.util.Random;

public class CollaborationServiceUnitTest implements MembreIdDataTest {

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
        // Configuration initiale de WebClient
        WebClient realWebClient = WebClient.create();

        // Utilisez WebClient pour effectuer une requête réelle ou simulée (avec WireMock par exemple)
        String response = realWebClient.get()
                .uri(WebClientConfig.MEMBRE_SERVICE_URL)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Asserts pour vérifier la réponse
        assertNotNull(response);
        // Autres vérifications pour s'assurer que la réponse est correcte
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

}

package com.attijarivos.unit;

import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.DataTest;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public abstract class WebClientTest implements DataTest {

    @Mock
    protected WebClient webClient;


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
}

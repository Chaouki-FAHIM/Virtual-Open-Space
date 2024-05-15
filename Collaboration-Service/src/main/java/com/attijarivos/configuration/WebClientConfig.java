package com.attijarivos.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig implements WebFluxConfigurer {

    public static final String MEMBRE_SERVICE_URL = "http://localhost:8081/membres";
    public static final String INVITATION_SERVICE_URL = "http://localhost:8082/invitations";

    @Bean
    @Qualifier("webClient-layer-config")
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}

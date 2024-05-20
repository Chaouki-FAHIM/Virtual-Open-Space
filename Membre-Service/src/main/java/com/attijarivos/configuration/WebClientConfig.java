package com.attijarivos.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration("webClient-layer-config")
public class WebClientConfig implements WebFluxConfigurer {

    public static final String INVITATION_SERVICE_URL = "http://localhost:8082/invitations";
    public static final String TEAM_SERVICE_URL = "http://localhost:8083/teams";

    @Bean
    @Qualifier("webClient-layer-config")
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

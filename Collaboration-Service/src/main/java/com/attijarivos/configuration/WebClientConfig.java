package com.attijarivos.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig implements WebFluxConfigurer {

    public static final String MEMBRE_SERVICE_URL = "http://membre-service/membres";
    public static final String INVITATION_SERVICE_URL = "http://collaboration-service/invitations";

    @Bean
    @Qualifier("webClient-layer-config")
    @LoadBalanced
    public WebClient.Builder webClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
}

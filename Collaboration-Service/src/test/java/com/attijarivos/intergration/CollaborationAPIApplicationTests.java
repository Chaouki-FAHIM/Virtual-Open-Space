package com.attijarivos;


import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.repository.CollaborationRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class CollaborationAPIApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CollaborationRepository collaborationRepository;
	ObjectMapper objectMapper = new ObjectMapper();
	private final String URI = "/collaboration";
	private String memberId;

	@DynamicPropertySource
	static public void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		//dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@AfterEach
	void cleanUp() {
		collaborationRepository.deleteAll();
	}

	private CollaborationRequest getMembreResquest() {
		return CollaborationRequest.builder()
				.confidentielle(true)
				.dateDepart(null)
				.titre("Collaboration Test")
				.idProprietaire("663bbf3e7a25633f8b25a610")
				.build();
	}
}
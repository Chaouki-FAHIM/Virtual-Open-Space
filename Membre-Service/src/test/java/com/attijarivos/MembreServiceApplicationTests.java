package com.attijarivos;

import com.attijarivos.dto.MembreRequest;
import com.attijarivos.dto.MembreResponse;
import com.attijarivos.model.RoleHabilation;
import com.attijarivos.repository.MembreRespository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class MembreServiceApplicationTests {

	@Container
	static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10");
	@Autowired
	private MockMvc mockMvc;
	ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private MembreRespository membreRespository;
	private final String URI = "/membres";
	private String memberId;

	@DynamicPropertySource
	static public void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@AfterEach
	void cleanUp() {
		membreRespository.deleteAll();
	}

	private MembreRequest getMembreResquest() {
		return MembreRequest.builder()
				.nom("FAHIM")
				.prenom("Chaouki")
				.roleHabilation(RoleHabilation.TEST)
				.build();
	}

	@Test
	public void shouldCreateMembre() throws Exception {
		MembreRequest membreRequest = getMembreResquest();
		String membreRequestString = objectMapper.writeValueAsString(membreRequest);
		byte[] responseContent = mockMvc.perform(
				MockMvcRequestBuilders.post(URI)
						.contentType(MediaType.APPLICATION_JSON)
						.content(membreRequestString)
		).andExpect(status().isCreated()).andReturn().getResponse().getContentAsByteArray();
		Assertions.assertEquals(1, membreRespository.findAll().size());
		memberId = objectMapper.readValue(responseContent, MembreResponse.class).getId();
	}

	@Test
	public void shouldGetAllMembresEmptyList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(URI)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void shouldGetAllMembresList() throws Exception {
		//shouldCreateMembre();
		MembreRequest membreRequest = getMembreResquest();
		String membreRequestString = objectMapper.writeValueAsString(membreRequest);
		mockMvc.perform(
				MockMvcRequestBuilders.post(URI)
						.contentType(MediaType.APPLICATION_JSON)
						.content(membreRequestString)
		).andExpect(status().isCreated());
		mockMvc.perform(MockMvcRequestBuilders.get(URI)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].nom", is("FAHIM")))
				.andExpect(jsonPath("$[0].prenom", is("Chaouki")))
				.andExpect(jsonPath("$[0].roleHabilation", is(RoleHabilation.TEST.toString())));
	}

	@Test
	public void shouldGetOnMembreById() throws Exception {
		shouldCreateMembre();
		// Testez la récupération du membre par son ID
		mockMvc.perform(MockMvcRequestBuilders.get(URI + "/" + memberId)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(memberId)))
				.andExpect(jsonPath("$.nom", is("FAHIM")))
				.andExpect(jsonPath("$.prenom", is("Chaouki")))
				.andExpect(jsonPath("$.roleHabilation", is(RoleHabilation.TEST.toString())));

	}
}
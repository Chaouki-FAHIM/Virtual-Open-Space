package com.attijarivos.intergration;

import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.IMembreID;
import com.attijarivos.repository.CollaborationRepository;
import com.attijarivos.repository.InvitationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class CollaborationAPIApplicationTests implements IMembreID {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CollaborationRepository collaborationRepository;
	@Autowired
	private InvitationRepository invitationRepository;

	private final String URI = "/collaborations";

	@AfterEach
	void cleanUp() {
		collaborationRepository.deleteAll();
		invitationRepository.deleteAll();
	}

	private static CollaborationRequest getCollaborationRequest() {
		return CollaborationRequest.builder()
				.confidentielle(true)
				.dateDepart(null)
				.titre("Collaboration Test")
				.idProprietaire(MEMBRE_ONE_ID)
				.build();
	}

	@Test
	void testCreateCollaboration() throws Exception {
		CollaborationRequest request = getCollaborationRequest();
		mockMvc.perform(
				MockMvcRequestBuilders.post(URI)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.titre").value("Collaboration Test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.confidentielle").value(true))
				.andExpect(MockMvcResultMatchers.jsonPath("$.idProprietaire").value(MEMBRE_ONE_ID));
	}

	@Test
	void testGetEmptyListOfCollaborations() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(URI))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
	}

	@Test
	void testGetAllCollaborations() throws Exception {
		testCreateCollaboration();
		mockMvc.perform(MockMvcRequestBuilders.get(URI))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].titre").value("Collaboration Test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].confidentielle").value(true))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].idProprietaire").value(MEMBRE_ONE_ID));
	}
}
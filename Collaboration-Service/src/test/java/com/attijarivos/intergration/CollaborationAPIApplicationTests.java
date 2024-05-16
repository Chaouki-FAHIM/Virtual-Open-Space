package com.attijarivos.intergration;

import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;
import com.attijarivos.DataTest;
import com.attijarivos.ICollaborationTest;
import com.attijarivos.model.Collaboration;
import com.attijarivos.repository.CollaborationRepository;
import com.attijarivos.repository.InvitationRepository;
import com.attijarivos.repository.ParticipationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional
@ActiveProfiles("test")
class CollaborationAPIApplicationTests implements DataTest, ICollaborationTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CollaborationRepository collaborationRepository;
	@Autowired
	private ParticipationRepository participationRepository;
	@Autowired
	private InvitationRepository invitationRepository;

	private final String URI = "/collaborations";

	@AfterEach
	void cleanUp() {
		collaborationRepository.deleteAll();
		participationRepository.deleteAll();
		invitationRepository.deleteAll();
	}

	@BeforeEach
	void setup() {
		cleanUp();
	}

	private int createCollaboration() throws Exception {
		MvcResult createResult = mockMvc.perform(
						MockMvcRequestBuilders.post(URI)
								.contentType(MediaType.APPLICATION_JSON)
								.content(new ObjectMapper().writeValueAsString(getCollaborationRequest()))
				)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();

		String responseString = createResult.getResponse().getContentAsString();
		return JsonPath.read(responseString, "$.idCollaboration");
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
				.andExpect(MockMvcResultMatchers.jsonPath("$.idProprietaire").value(FIRST_MEMBRE_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$.dateDepart").isNotEmpty());
	}

	@Test
	void testGetOneCollaboration() throws Exception {
		long idCollaborationCreated= createCollaboration();

		mockMvc.perform(MockMvcRequestBuilders.get(URI+"/"+idCollaborationCreated))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.idCollaboration").value(idCollaborationCreated))
				.andExpect(MockMvcResultMatchers.jsonPath("$.titre").value("Collaboration Test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.confidentielle").value(true))
				.andExpect(MockMvcResultMatchers.jsonPath("$.idProprietaire").value(FIRST_MEMBRE_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$.dateDepart").isNotEmpty());
	}

	@Test
	void testGetMembersCollaboration() throws Exception {

		long idCollaborationCreated= createCollaboration();
		mockMvc.perform(MockMvcRequestBuilders.get(URI+"/"+ idCollaborationCreated +"/uninvited-members"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
	}

	@Test
	void testGetEmptyListOfCollaborations() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(URI))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
	}

	@Test
	void testGetAllCollaborations() throws Exception {
		long idCollaborationCreated= createCollaboration();
		mockMvc.perform(MockMvcRequestBuilders.get(URI))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].idCollaboration").value(idCollaborationCreated))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].titre").value("Collaboration Test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].confidentielle").value(true))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].idProprietaire").value(FIRST_MEMBRE_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].dateDepart").isNotEmpty());
	}

	@Test
	void testUpdateCollaboration() throws Exception {

		long idCollaborationCreated= createCollaboration();

		CollaborationUpdateRequest updateRequest = getCollaborationUpdateRequest();

		mockMvc.perform(
				MockMvcRequestBuilders.put(URI + "/"+idCollaborationCreated)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(updateRequest))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.titre").value("Collaboration Test Updated"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.confidentielle").value(false))
				.andExpect(MockMvcResultMatchers.jsonPath("$.dateDepart").isNotEmpty())
				.andReturn();
	}

	@Test
	void testDeleteCollaboration() throws Exception {
		long idCollaborationCreated= createCollaboration();

		mockMvc.perform(MockMvcRequestBuilders.delete(URI + "/"+idCollaborationCreated))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testJoinCollaboration() throws Exception {
		long idCollaborationCreated= createCollaboration();

		JoinCollaborationRequest request = JoinCollaborationRequest.builder().idMembre(SECOND_MEMBRE_ID).build();

		mockMvc.perform(MockMvcRequestBuilders.patch(URI+"/"+idCollaborationCreated+"/join")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(request))
				)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.idCollaboration").value(idCollaborationCreated))
				.andExpect(MockMvcResultMatchers.jsonPath("$.titre").value("Collaboration Test"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.confidentielle").value(true))
				.andExpect(MockMvcResultMatchers.jsonPath("$.idProprietaire").value(FIRST_MEMBRE_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$.dateDepart").isNotEmpty());

//		assertTrue(
//                participationRepository.findByIdParticipantAndCollaboration(FIRST_MEMBRE_ID, searchCollaborationById(COLLABORATION_ID)).isPresent(),
//				"The new participant should be associated with the collaboration."
//		);
	}


	private Collaboration searchCollaborationById(Long collaborationId) {
		return collaborationRepository.findById(collaborationId).get();
	}
}
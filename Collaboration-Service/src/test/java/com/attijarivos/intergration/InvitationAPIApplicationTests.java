package com.attijarivos.intergration;


import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.request.InvitationListRequest;
import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.DataTest;
import com.attijarivos.ICollaborationTest;
import com.attijarivos.model.Collaboration;
import com.attijarivos.repository.CollaborationRepository;
import com.attijarivos.repository.InvitationRepository;
import com.attijarivos.repository.ParticipationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class InvitationAPIApplicationTests implements DataTest, ICollaborationTest {

	@Autowired
	private MockMvc mockMvc;

	private final String URI = "/invitations";

	@Autowired
	private CollaborationRepository collaborationRepository;
	@Autowired
	private ParticipationRepository participationRepository;
	@Autowired
	private InvitationRepository invitationRepository;

	private final Long COLLABORATION_ID = 3L; // aleardy we have 2 collaborations



	@AfterEach
	void cleanUp() {
		invitationRepository.deleteAll();
		collaborationRepository.deleteAll();
		participationRepository.deleteAll();
	}

	@BeforeEach
	void setup() {
		cleanUp();
	}

	private static InvitationRequest getInvitationRequest(long collaborationId) {
		return InvitationRequest.builder()
				.idInvite(SECOND_MEMBRE_ID)
				.idCollaboration(collaborationId)
				.build();
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


	Collaboration createCollaboration(long collaborationId) {
		CollaborationRequest collaborationRequest = getCollaborationRequest();
		return collaborationRepository.save(
				Collaboration.builder()
						.idCollaboration(collaborationId)
						.confidentielle(collaborationRequest.getConfidentielle())
						.dateDepart(collaborationRequest.getDateDepart())
						.titre(collaborationRequest.getTitre())
						.idProprietaire(collaborationRequest.getIdProprietaire())
						.dateCreationCollaboration(new Date())
						.build()
		);
	}

	private int createInvitation() throws Exception {
		InvitationRequest invitationRequest = getInvitationRequest(
				createCollaboration(COLLABORATION_ID).getIdCollaboration()
		);

		MvcResult createResult = mockMvc.perform(
						MockMvcRequestBuilders.post(URI)
								.contentType(MediaType.APPLICATION_JSON)
								.content(new ObjectMapper().writeValueAsString(invitationRequest)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();

		String responseString = createResult.getResponse().getContentAsString();
		return JsonPath.read(responseString, "$.idInvitation");
	}

	@Test
	void testCreateOneInvitation() throws Exception {
		InvitationRequest invitationRequest = getInvitationRequest(
				createCollaboration(COLLABORATION_ID).getIdCollaboration()
		);

		mockMvc.perform(
						MockMvcRequestBuilders.post(URI)
								.contentType(MediaType.APPLICATION_JSON)
								.content(new ObjectMapper().writeValueAsString(invitationRequest)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.idInvite").value(SECOND_MEMBRE_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$.collaboration").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.dateCreationInvitation").isNotEmpty());
	}

	@Test
	void testCreateListInvitations() throws Exception {
		InvitationRequest invitationRequest = getInvitationRequest(
				createCollaboration(COLLABORATION_ID).getIdCollaboration()
		);

		List<String> idInvites = new ArrayList<>();
		idInvites.add(FIRST_MEMBRE_ID);
		idInvites.add(SECOND_MEMBRE_ID);

		InvitationListRequest request =
				InvitationListRequest.builder()
						.idCollaboration(createCollaboration(COLLABORATION_ID).getIdCollaboration())
						.idInvites(idInvites)
						.build();

		mockMvc.perform(
						MockMvcRequestBuilders.post(URI+"/list")
								.contentType(MediaType.APPLICATION_JSON)
								.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].idInvite").value(FIRST_MEMBRE_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].collaboration").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].dateCreationInvitation").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].idInvite").value(SECOND_MEMBRE_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].collaboration").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].dateCreationInvitation").isNotEmpty());
	}

	@Test
	void testDeleteInvitation() throws Exception {
		long idInvitationCreated=  createInvitation();
		mockMvc.perform(MockMvcRequestBuilders.delete(URI+"/"+idInvitationCreated))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}


	@Test
	void testGetEmptyListOfInvitations() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(URI))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void testGetAllInvitations() throws Exception {
		long idInvitationCreated=  createInvitation();

		mockMvc.perform(MockMvcRequestBuilders.get(URI))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].idInvitation").value(idInvitationCreated))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].idInvite").value(SECOND_MEMBRE_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].collaboration").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].dateCreationInvitation").isNotEmpty());
	}
}
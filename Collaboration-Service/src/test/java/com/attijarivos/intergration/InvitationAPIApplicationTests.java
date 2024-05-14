package com.attijarivos.intergration;


import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.IntegrationTest;
import com.attijarivos.model.Collaboration;
import com.attijarivos.model.Invitation;
import com.attijarivos.repository.CollaborationRepository;
import com.attijarivos.repository.InvitationRepository;
import com.attijarivos.repository.ParticipationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.attijarivos.intergration.CollaborationAPIApplicationTests.getCollaborationRequest;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class InvitationAPIApplicationTests extends IntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	private final String URI = "/invitations";

	@Autowired
	private CollaborationRepository collaborationRepository;
	@Autowired
	private ParticipationRepository participationRepository;
	@Autowired
	private InvitationRepository invitationRepository;


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

	Collaboration createCollaboration(long collaborationId) {
		CollaborationRequest collaborationRequest = getCollaborationRequest();
		return collaborationRepository.save(
				Collaboration.builder()
						.idCollaboration(collaborationId)
						.confidentielle(collaborationRequest.getConfidentielle())
						.dateDepart(collaborationRequest.getDateDepart())
						.titre(collaborationRequest.getTitre())
						.IdProprietaire(collaborationRequest.getIdProprietaire())
						.dateCreationCollaboration(DATE)
						.build()
		);
	}

	// les valeurs des ids incrémentées par 1 car cette méthode prendre de temps alors
	@Test
	void testCreateInvitation() throws Exception {
		InvitationRequest invitationRequest = getInvitationRequest(COLLABORATION_ID+1L);
		Collaboration collaboration = createCollaboration(COLLABORATION_ID+1L);

		log.warn("createInvitation ---> "+ collaboration.getIdCollaboration());

		mockMvc.perform(
						MockMvcRequestBuilders.post(URI)
								.contentType(MediaType.APPLICATION_JSON)
								.content(new ObjectMapper().writeValueAsString(invitationRequest)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.idInvitation").value(INVITATION_ID+1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.idInvite").value(SECOND_MEMBRE_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$.collaboration").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.dateCreationInvitation").isNotEmpty());
	}


	@Test
	void testGetEmptyListOfInvitations() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(URI))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0))
				);
	}

	@Test
	void testGetAllInvitations() throws Exception {
		InvitationRequest invitationRequest = getInvitationRequest(COLLABORATION_ID);
		Collaboration collaboration = createCollaboration(COLLABORATION_ID);

		invitationRepository.save(
				Invitation.builder()
						.idInvite(invitationRequest.getIdInvite())
						.dateCreationInvitation(DATE)
						.collaboration(collaboration)
						.build()
		);


		mockMvc.perform(MockMvcRequestBuilders.get(URI))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].idInvitation").value(INVITATION_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].idInvite").value(SECOND_MEMBRE_ID))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].collaboration").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].dateCreationInvitation").isNotEmpty());
	}
}
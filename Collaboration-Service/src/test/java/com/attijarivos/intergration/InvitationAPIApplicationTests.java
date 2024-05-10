package com.attijarivos.intergration;


import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;
import com.attijarivos.IMembreID;
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
class InvitationAPIApplicationTests implements IMembreID {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private InvitationRepository invitationRepository;
	private final String URI = "/invitations";

	@AfterEach
	void cleanUp() {
		invitationRepository.deleteAll();
	}

	private InvitationRequest getInvitationRequest() {
		return InvitationRequest.builder()
				.idInvite(MEMBRE_TWO_ID)
				.idCollaboration(1L)
				.build();
	}

	private JoinCollaborationRequest getInvitationUpdateRequest() {
		return JoinCollaborationRequest.builder()
				.dateParticiaption(TO_DAY)
				.build();
	}

	@Test
	void testCreateInvitation() throws Exception {
		InvitationRequest invitationRequest = getInvitationRequest();
		mockMvc.perform(
						MockMvcRequestBuilders.post(URI)
								.contentType(MediaType.APPLICATION_JSON)
								.content(new ObjectMapper().writeValueAsString(invitationRequest)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.dateParticiaption").value(TO_DAY)
				);
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
		testCreateInvitation();
		mockMvc.perform(MockMvcRequestBuilders.get(URI))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$[0].dateParticiaption").value(TO_DAY)
				);
	}
}
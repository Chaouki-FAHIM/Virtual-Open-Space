package com.attijarivos;


import com.attijarivos.model.Collaboration;
import com.attijarivos.model.Invitation;
import com.attijarivos.model.Participation;
import com.attijarivos.repository.CollaborationRepository;
import com.attijarivos.repository.InvitationRepository;
import com.attijarivos.repository.ParticipationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SpringBootApplication
public class CollaborationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborationServiceApplication.class, args);
	}

	private final String idProprietaire = "663bbf3e7a25633f8b25a610";
	private final String idInvite1 = "663bbf3e7a25633f8b25a611";
	private final String idInvite2 = "663bbf3e7a25633f8b25a612";


	@Bean
	CommandLineRunner start(CollaborationRepository collaborationRepository, InvitationRepository invitationRepository, ParticipationRepository participationRepository) {

		Date currentDate =new Date();

		return args -> {

			Collaboration collaboration1 = collaborationRepository.save(
					Collaboration.builder().titre("Collaboration 1").dateCreationCollaboration(currentDate).dateDepart(currentDate).confidentielle(false).idProprietaire(idProprietaire).visible(true).build()
			);

			Collaboration collaboration2 = collaborationRepository.save(
					Collaboration.builder().titre("Collaboration 2").dateCreationCollaboration(currentDate).dateDepart(currentDate).confidentielle(true).idProprietaire(idProprietaire).visible(true).build()
			);

			participationRepository.save(
					Participation.builder().collaboration(collaboration1).idParticipant(collaboration1.getIdProprietaire()).dateParticiaption(currentDate).build()
			);

			invitationRepository.save(
					Invitation.builder().idInvite(idInvite1).dateCreationInvitation(currentDate).collaboration(collaboration1).build()
			);

			invitationRepository.save(
					Invitation.builder().idInvite(idInvite2).dateCreationInvitation(currentDate).collaboration(collaboration1).build()
			);

			invitationRepository.save(
					Invitation.builder().idInvite(idInvite1).dateCreationInvitation(currentDate).collaboration(collaboration2).build()
			);

		};
	}

}

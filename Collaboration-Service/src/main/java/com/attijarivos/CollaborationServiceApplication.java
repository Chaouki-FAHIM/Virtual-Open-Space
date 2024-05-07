package com.attijarivos;


import com.attijarivos.DTO.MembreResponse;
import com.attijarivos.model.Collaboration;
import com.attijarivos.repository.CollaborationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class CollaborationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollaborationServiceApplication.class, args);
	}

	private final String idProprietaire = "";

	@Bean
	CommandLineRunner start(CollaborationRepository collaborationRepository) {
		return args -> {
			collaborationRepository.save(
					Collaboration.builder().titre("Collaboration 1").dateCreationCollaboration(new Date()).dateDepart(new Date()).confidentielle(false).IdProprietaire(idProprietaire).build()
			);

			collaborationRepository.save(
					Collaboration.builder().titre("Collaboration 2").dateCreationCollaboration(new Date()).dateDepart(new Date()).confidentielle(true).IdProprietaire(idProprietaire).build()
			);

			collaborationRepository.save(
					Collaboration.builder().titre("Collaboration 3").dateCreationCollaboration(new Date()).dateDepart(new Date()).confidentielle(false).IdProprietaire(idProprietaire).build()
			);

		};
	}

}

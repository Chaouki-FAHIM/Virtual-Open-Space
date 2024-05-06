package com.attijarivos;


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

	@Bean
	CommandLineRunner start(CollaborationRepository collaborationRepository) {
		return args -> {
			collaborationRepository.save(
					Collaboration.builder().titre("Collaboration 1").dateDepart(new Date()).confidentielle(false).proprietaire(1L).build()
			);

			collaborationRepository.save(
					Collaboration.builder().titre("Collaboration 2").dateDepart(new Date()).confidentielle(true).proprietaire(2L).build()
			);

			collaborationRepository.save(
					Collaboration.builder().titre("Collaboration 3").dateDepart(new Date()).confidentielle(false).proprietaire(3L).build()
			);

		};
	}

}

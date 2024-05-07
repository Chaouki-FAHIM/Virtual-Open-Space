package com.attijarivos;

import com.attijarivos.model.Membre;
import com.attijarivos.model.RoleHabilation;
import com.attijarivos.repository.MembreRespository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class MembreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MembreServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(MembreRespository membreRespository) {
		return args -> {
			membreRespository.save(
					Membre.builder().nom("Nom 1").prenom("Prénom 1").roleHabilation(RoleHabilation.DESIGN).statutCollaboration(true).build()
			);

			membreRespository.save(
					Membre.builder().nom("Nom 2").prenom("Prénom 2").roleHabilation(RoleHabilation.BACKEND).statutCollaboration(true).build()
			);

			membreRespository.save(
					Membre.builder().nom("Nom 3").prenom("Prénom 3").roleHabilation(RoleHabilation.TEST).statutCollaboration(false).build()
			);

		};
	}

}

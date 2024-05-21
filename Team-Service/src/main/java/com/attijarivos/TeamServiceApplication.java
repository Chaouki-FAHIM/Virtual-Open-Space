package com.attijarivos;

import com.attijarivos.model.Siege;
import com.attijarivos.model.Team;
import com.attijarivos.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TeamServiceApplication {
    public static void main( String[] args )
    {
        SpringApplication.run(TeamServiceApplication.class, args);
    }

//    @Bean
//    CommandLineRunner start(TeamRepository teamRepository) {
//        return args -> {
//            teamRepository.save(
//                    Team.builder().nomTeam("Self care").descriptionTeam("Mobile Application").siege(Siege.HASSAN_2).build()
//            );
//
//            teamRepository.save(
//                    Team.builder().nomTeam("Data office").descriptionTeam("Traitement des données").siege(Siege.ROUDANI).build()
//            );
//
//            teamRepository.save(
//                    Team.builder().nomTeam("Borj Office").descriptionTeam("Borj Office").siege(Siege.HASSAN_2).build()
//            );
//
//        };
//    }
}

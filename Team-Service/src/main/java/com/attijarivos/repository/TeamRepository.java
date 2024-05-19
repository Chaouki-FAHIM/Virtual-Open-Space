package com.attijarivos.repository;

import com.attijarivos.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeamRepository extends MongoRepository<Team, String> {
    Optional<Team> findByIdTeam(String idTeam);
}

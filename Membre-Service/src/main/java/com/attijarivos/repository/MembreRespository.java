package com.attijarivos.repository;

import com.attijarivos.model.Membre;
import io.micrometer.common.KeyValues;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Set;

public interface MembreRespository extends MongoRepository<Membre,String> {

    Set<Membre> findByNomMembreAndPrenom(String nomMembre, String prenom);
    Set<Membre> findByNomMembreContainingIgnoreCaseOrPrenomContainingIgnoreCase(String firstName, String lastName);
}

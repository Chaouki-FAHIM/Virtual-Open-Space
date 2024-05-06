package com.attijarivos.repository;

import com.attijarivos.model.Membre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MembreRespository extends MongoRepository<Membre,String> {
}

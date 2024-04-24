package com.attijarivos.membreservice.repository;

import com.attijarivos.membreservice.model.Membre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MembreRespository extends MongoRepository<Membre,String> {
}

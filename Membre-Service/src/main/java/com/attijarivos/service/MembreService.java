package com.attijarivos.service;

import com.attijarivos.dto.MembreResponse;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.dto.MembreRequest;
import com.attijarivos.mapper.MembreMapper;
import com.attijarivos.model.Membre;
import com.attijarivos.repository.MembreRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MembreService {

    private final MembreRespository membreRespository;

    private final MembreMapper membreMapper;

    public MembreResponse createMembre(MembreRequest membreRequest) throws RequiredDataException {

        if(membreRequest.getNom() == null  || Objects.equals(membreRequest.getNom(), "")) {
            throw new RequiredDataException("Nom est obligatoire pour l'ajout d'un nouveau membre");
        }
        if(membreRequest.getPrenom() == null  || Objects.equals(membreRequest.getPrenom(), "")) {
            throw new RequiredDataException("Prénom est obligatoire pour l'ajout d'un nouveau membre");
        }
        if(membreRequest.getRoleHabilation()== null ) {
            throw new RequiredDataException("Rôle d'habilation est obligatoire pour l'ajout d'un nouveau membre");
        }

        Membre membre = membreMapper.fromReqToMembre(membreRequest);
        membreRespository.save(membre);

        log.info("Membre d'id {} est enregistré!!",membre.getId());

        return membreMapper.fromMembreToRes(membre);
    }

    public List<MembreResponse> getAllMembres() {
        List<Membre> membres = membreRespository.findAll();
        log.info("Membres tourvés sont : {}",membres.toString());
        return membres.stream().map(membreMapper::fromMembreToRes).toList();
    }

    public MembreResponse getMembre(String id) throws NotFoundDataException {
        Optional<Membre> membre = membreRespository.findById(id);
        log.info("Membre tourvé est : {}",membre);
        if(membre.isEmpty()) throw new NotFoundDataException(id);
        return membre.map(membreMapper::fromMembreToRes).orElse(null);
    }
}

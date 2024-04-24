package com.attijarivos.membreservice.mapper;

import com.attijarivos.membreservice.dto.MembreRequest;
import com.attijarivos.membreservice.dto.MembreResponse;
import com.attijarivos.membreservice.model.Membre;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MembreMapper {

    public Membre fromReqToMembre(MembreRequest membreRequest) {

        return Membre.builder()
                .nom(membreRequest.getNom())
                .prenom(membreRequest.getPrenom())
                .roleHabilation(membreRequest.getRoleHabilation())
                .build();
    }

    public MembreResponse fromMembreToRes(Membre membre) {
        MembreResponse membreResponse = new MembreResponse();
        BeanUtils.copyProperties(membre, membreResponse);
        return membreResponse;
    }

}

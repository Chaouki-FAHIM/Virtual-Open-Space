package com.attijarivos.mapper;

import com.attijarivos.dto.request.MembreUpdateRequest;
import com.attijarivos.dto.response.details.DetailMembreResponse;
import com.attijarivos.dto.response.details.DetailTeamResponse;
import com.attijarivos.dto.response.shorts.ShortMembreResponse;
import com.attijarivos.dto.request.MembreRequest;
import com.attijarivos.dto.response.shorts.ShortTeamResponse;
import com.attijarivos.model.Membre;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MembreMapper {

    public Membre fromReqToMembre(MembreRequest membreRequest) {

        return Membre.builder()
                .nomMembre(membreRequest.getNomMembre())
                .prenom(membreRequest.getPrenom())
                .roleHabilation(membreRequest.getRoleHabilation())
                .build();
    }

    public ShortMembreResponse fromMembreToRes(Membre membre) {
        ShortMembreResponse shortMembreResponse = new ShortMembreResponse();
        BeanUtils.copyProperties(membre, shortMembreResponse);
        return shortMembreResponse;
    }

    public DetailMembreResponse fromMembreToDetail(Membre membre, List<DetailTeamResponse> teams) {
        DetailMembreResponse detailMembreResponse = new DetailMembreResponse();
        BeanUtils.copyProperties(membre, detailMembreResponse);

        Set<ShortTeamResponse> detailTeamResponseSet = new HashSet<>();

        for (DetailTeamResponse detailTeamResponse : teams)
            for (ShortMembreResponse shortMembreResponse : detailTeamResponse.getMembres()) {
                if (shortMembreResponse.getIdMembre() != null && shortMembreResponse.getIdMembre().equals(membre.getIdMembre())) {
                    detailTeamResponseSet.add(
                            ShortTeamResponse.builder()
                                    .idTeam(detailTeamResponse.getIdTeam())
                                    .nomTeam(detailTeamResponse.getNomTeam())
                                    .siege(detailTeamResponse.getSiege())
                                    .build()
                    );
                    break;
                }

        }

        detailMembreResponse.setTeams(detailTeamResponseSet);
        return detailMembreResponse;
    }

    public MembreRequest fromReqToUpdateMembre(MembreUpdateRequest membreUpdateRequest) {
        MembreRequest membreRequest = new MembreRequest();
        BeanUtils.copyProperties(membreUpdateRequest, membreRequest);
        return membreRequest;
    }

}

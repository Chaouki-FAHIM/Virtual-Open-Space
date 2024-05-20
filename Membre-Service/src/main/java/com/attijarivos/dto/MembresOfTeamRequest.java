package com.attijarivos.dto;


import java.util.List;

public class MembresOfTeamRequest {

    private List<String> idMembres;

    public List<String> getIdMembres() {
        return idMembres;
    }

    public void setIdMembres(List<String> idMembres) {
        this.idMembres = idMembres;
    }
}

package com.attijarivos.service;


import com.attijarivos.exception.*;

import java.util.List;
import java.util.Objects;


public interface IService <RequestDTO,JoinReq,ResponseDTO,ID> {

    default Boolean isNotNullValue(Object value) {
        return value == null || Objects.equals(value, "");
    }

    ResponseDTO createOne(RequestDTO requestDTO) throws RequiredDataException, NotFoundDataException, RededicationInvitationException, MicroserviceAccessFailureException;
    List<ResponseDTO> getAll();
    ResponseDTO getOne(ID id) throws NotFoundDataException;
    void delete(ID id) throws NotFoundDataException,RequiredDataException;
    ResponseDTO rejoindre(ID id, JoinReq joinRequest) throws NotFoundDataException, RequiredDataException;
}

package com.attijarivos.service;


import com.attijarivos.exception.*;

import java.util.List;
import java.util.Objects;


public interface IService <RequestDTO,ResponseDTO,ID> {

    default Boolean isNotNullValue(Object value) {
        return value == null || Objects.equals(value, "");
    }

    ResponseDTO createOne(RequestDTO requestDTO) throws RequiredDataException, NotFoundDataException, RededicationInvitationException, MicroserviceAccessFailureException, NotValidOwnerInviteException;
    List<ResponseDTO> getAll() throws NotFoundDataException;
    ResponseDTO getOne(ID id) throws NotFoundDataException;
    boolean delete(ID id) throws NotFoundDataException,RequiredDataException;
}

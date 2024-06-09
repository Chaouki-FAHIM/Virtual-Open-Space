package com.attijarivos.service;


import com.attijarivos.exception.*;

import java.util.Objects;
import java.util.Set;


public interface IService <RequestDTO,ResponseDTO,ID> {

    default Boolean isNotNullValue(Object value) {
        return value == null || Objects.equals(value, "");
    }

    ResponseDTO createOne(RequestDTO requestDTO) throws Exception;
    Set<ResponseDTO> getAll() throws NotFoundDataException, MicroserviceAccessFailureException;
    ResponseDTO getOne(ID id) throws NotFoundDataException, MicroserviceAccessFailureException;
    boolean delete(ID id) throws NotFoundDataException,RequiredDataException;
}

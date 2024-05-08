package com.attijarivos.service;


import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.NotValidDataException;
import com.attijarivos.exception.RequiredDataException;

import java.util.List;
import java.util.Objects;


public interface IService <RequestDTO,ResponseDTO,ID> {

    default Boolean isNotNullValue(Object value) {
        return value == null || Objects.equals(value, "");
    }

    ResponseDTO create(RequestDTO requestDTO) throws RequiredDataException, NotValidDataException, NotFoundDataException;
    List<ResponseDTO> getAll();
    ResponseDTO getOne(ID id) throws NotFoundDataException;
    ResponseDTO update(ID id, RequestDTO requestDTO) throws NotFoundDataException,RequiredDataException,NotValidDataException;
    void delete(ID id) throws NotFoundDataException,RequiredDataException,NotValidDataException;
}

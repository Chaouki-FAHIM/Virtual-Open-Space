package com.attijarivos.service;



import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.NotValidDataException;
import com.attijarivos.exception.RequiredDataException;

import java.util.List;

public interface IService <RequestDTO,ResponseDTO,ID> {
    ResponseDTO create(RequestDTO requestDTO) throws RequiredDataException, NotValidDataException;
    List<ResponseDTO> getAll();
    ResponseDTO getOne(ID id) throws NotFoundDataException;
    ResponseDTO update(ID id, RequestDTO requestDTO) throws NotFoundDataException,RequiredDataException,NotValidDataException;
    void delete(ID id) throws NotFoundDataException,RequiredDataException,NotValidDataException;
}

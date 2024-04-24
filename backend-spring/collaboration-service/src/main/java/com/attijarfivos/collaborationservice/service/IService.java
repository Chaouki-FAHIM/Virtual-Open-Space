package com.attijarfivos.collaborationservice.service;

import com.attijarfivos.collaborationservice.exception.NotFoundDataException;
import com.attijarfivos.collaborationservice.exception.NotValidDataException;
import com.attijarfivos.collaborationservice.exception.RequiredDataException;

import java.util.List;

public interface IService <RequestDTO,ResponseDTO,ID> {
    ResponseDTO create(RequestDTO requestDTO) throws RequiredDataException,NotValidDataException;
    List<ResponseDTO> getAll();
    ResponseDTO getOne(ID id) throws NotFoundDataException;
    ResponseDTO update(ID id, RequestDTO requestDTO) throws NotFoundDataException;
    ResponseDTO update(ID id) throws NotFoundDataException,RequiredDataException,NotValidDataException;
}

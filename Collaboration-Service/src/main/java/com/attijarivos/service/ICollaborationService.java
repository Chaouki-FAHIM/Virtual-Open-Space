package com.attijarivos.service;


import com.attijarivos.DTO.request.JoinInvitationRequest;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.NotValidDataException;
import com.attijarivos.exception.RequiredDataException;



public interface ICollaborationService<RequestDTO,ResponseDTO,ID> extends  IService <RequestDTO, JoinInvitationRequest,ResponseDTO,ID>{

    ResponseDTO update(ID id, RequestDTO requestDTO) throws NotFoundDataException,RequiredDataException,NotValidDataException;
}

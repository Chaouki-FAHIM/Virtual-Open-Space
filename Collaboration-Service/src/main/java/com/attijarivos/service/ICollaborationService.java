package com.attijarivos.service;


import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;

import java.util.List;


public interface ICollaborationService<RequestDTO,ResponseDTO,ID> extends  IService <RequestDTO, JoinCollaborationRequest,ResponseDTO,ID>{

    ResponseDTO update(ID id, CollaborationUpdateRequest requestDTO) throws NotFoundDataException,RequiredDataException;
    List<MembreResponse> getUninvitedMembersToCollaboration(ID id) throws NotFoundDataException, MicroserviceAccessFailureException;
}

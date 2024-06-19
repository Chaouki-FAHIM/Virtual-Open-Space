package com.attijarivos.service;


import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;
import com.attijarivos.DTO.response.MembreResponse;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;

import java.util.List;
import java.util.Set;


public interface ICollaborationService<RequestDTO,ResponseDTO,ID> extends  IService <RequestDTO,ResponseDTO,ID>{

    ResponseDTO update(ID id, CollaborationUpdateRequest requestDTO) throws NotFoundDataException,RequiredDataException;
    ResponseDTO joindre(ID id, JoinCollaborationRequest joinRequest) throws Exception;
    List<MembreResponse> getMembersForJoiningCollaboration(ID id) throws NotFoundDataException, MicroserviceAccessFailureException;
    Set<ResponseDTO> searchCollaboration(String collaborationTitle) throws NotFoundDataException, MicroserviceAccessFailureException;
}

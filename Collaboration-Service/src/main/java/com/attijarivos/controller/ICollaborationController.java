package com.attijarivos.controller;


import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;

import com.attijarivos.exception.CollaborationAccessDeniedException;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import org.springframework.http.ResponseEntity;


public interface ICollaborationController<Request, ID> extends IController <Request, ID>{
    ResponseEntity<?> update(ID id, CollaborationUpdateRequest collaborationRequest) throws RequiredDataException, NotFoundDataException;
    ResponseEntity<?> joindre(ID id, JoinCollaborationRequest joindreReq) throws RequiredDataException, NotFoundDataException, CollaborationAccessDeniedException;
    ResponseEntity<?> getMembersForJoiningCollaboration(ID id) throws NotFoundDataException, MicroserviceAccessFailureException;
    ResponseEntity<?> searchCollaboration(String titleCollaboration) throws NotFoundDataException;
}

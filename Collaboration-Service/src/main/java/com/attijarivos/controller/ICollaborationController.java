package com.attijarivos.controller;


import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;

import org.springframework.http.ResponseEntity;


public interface ICollaborationController<Request, ID> extends IController <Request, ID>{
    ResponseEntity<?> update(ID id, CollaborationUpdateRequest collaborationRequest);
    ResponseEntity<?> joindre(ID id, JoinCollaborationRequest joindreReq);
    ResponseEntity<?> getMembersForJoiningCollaboration(ID id);
}

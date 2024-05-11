package com.attijarivos.controller;


import com.attijarivos.DTO.request.InvitationListRequest;
import com.attijarivos.DTO.request.JoinInvitationRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IInvitationController <Request, ID> extends IController <Request, JoinInvitationRequest, ID>{
    ResponseEntity<?> createInvitationList(InvitationListRequest requestDTOList);
}

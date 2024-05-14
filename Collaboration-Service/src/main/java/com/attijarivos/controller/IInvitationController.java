package com.attijarivos.controller;


import com.attijarivos.DTO.request.InvitationListRequest;
import org.springframework.http.ResponseEntity;

public interface IInvitationController <Request, ID> extends IController <Request, ID>{
    ResponseEntity<?> createInvitationList(InvitationListRequest requestDTOList);
}

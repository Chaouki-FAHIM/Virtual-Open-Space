package com.attijarivos.controller;


import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IControllerInvitation <Request, ID> extends IController <Request, ID>{
    ResponseEntity<?> createInvitationList(List<Request> requestDTOList) ;
}

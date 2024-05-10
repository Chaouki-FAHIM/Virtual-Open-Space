package com.attijarivos.controller;


import com.attijarivos.DTO.request.JoindreRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IControllerInvitation <Request, ID> extends IController <Request, ID>{
    ResponseEntity<?> createInvitationList(List<Request> requestDTOList) ;
    ResponseEntity<?> joindre(List<JoindreRequest> Request) ;
}

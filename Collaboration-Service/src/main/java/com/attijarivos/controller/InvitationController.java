package com.attijarivos.controller;


import com.attijarivos.DTO.InvitationRequest;
import com.attijarivos.DTO.InvitationResponse;
import com.attijarivos.service.IService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invitations")
@RequiredArgsConstructor
@Slf4j
public class InvitationController implements IController<InvitationRequest,Long> {

    @Autowired
    @Qualifier("service-layer-invitation")
    private final IService<InvitationRequest, InvitationResponse,Long> invitationService;


    @Override
    public ResponseEntity<?> create(InvitationRequest invitationRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<?> getOne(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(Long aLong, InvitationRequest invitationRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(Long aLong) {
        return null;
    }
}

package com.attijarivos.controller;


import com.attijarivos.DTO.request.InvitationRequest;
import com.attijarivos.DTO.request.JoinInvitationRequest;
import com.attijarivos.DTO.response.InvitationResponse;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.service.IInvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitations")
@RequiredArgsConstructor
@Slf4j
public class InvitationController implements IInvitationController<InvitationRequest,Long> {

    @Autowired
    @Qualifier("service-layer-invitation")
    private final IInvitationService<InvitationRequest,InvitationResponse,Long> invitationService;


    @PostMapping
    @Override
    public ResponseEntity<?> createOne(@RequestBody @Valid InvitationRequest invitationRequest) {
        try {
            InvitationResponse invitationResponse = invitationService.createOne(invitationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(invitationResponse);
        } catch (RequiredDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/list")
    @Override
    public ResponseEntity<?> createInvitationList(@RequestBody List<InvitationRequest> invitationRequestList) {
        try {
            List<InvitationResponse> invitationResponseList = invitationService.createInvitationList(invitationRequestList);
            return ResponseEntity.status(HttpStatus.CREATED).body(invitationResponseList);
        } catch (RequiredDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/join")
    @Override
    public ResponseEntity<?> joindre(@PathVariable("id") Long idInvitation, @RequestBody @Valid JoinInvitationRequest joinInvitationRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(invitationService.joindre(idInvitation, joinInvitationRequest));
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RequiredDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAll() {
        try {
            List<InvitationResponse> invitationsResponse = invitationService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(invitationsResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getOne(@PathVariable("id") Long idInvitation) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(invitationService.getOne(idInvitation));
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long idInvitation) {
        try {
            invitationService.delete(idInvitation);
            return ResponseEntity.status(HttpStatus.OK).body("Bonne Suppression de l'invitation d'id : "+idInvitation);
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

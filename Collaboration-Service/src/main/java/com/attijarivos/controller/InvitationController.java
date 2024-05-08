package com.attijarivos.controller;


import com.attijarivos.DTO.InvitationRequest;
import com.attijarivos.DTO.InvitationResponse;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.service.IService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitations")
@RequiredArgsConstructor
@Slf4j
public class InvitationController implements IController<InvitationRequest,Long> {

    @Autowired
    @Qualifier("service-layer-invitation")
    private final IService<InvitationRequest, InvitationResponse,Long> invitationService;


    @PostMapping
    @Override
    public ResponseEntity<?> create(@RequestBody @Valid InvitationRequest invitationRequest) {
        try {
            InvitationResponse invitationResponse = invitationService.create(invitationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(invitationResponse);
        } catch (RequiredDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAll() {
        return null;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getOne(@PathVariable("id") Long idInvitation) {
        return null;
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> update(@PathVariable("id") Long idInvitation,@RequestBody @Valid InvitationRequest invitationRequest) {
        return null;
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long idInvitation) {
        return null;
    }
}

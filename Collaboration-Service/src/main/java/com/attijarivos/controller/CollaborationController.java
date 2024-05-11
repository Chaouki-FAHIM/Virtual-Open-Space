package com.attijarivos.controller;


import com.attijarivos.DTO.request.CollaborationRequest;
import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.exception.CollaborationAccessDeniedException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.service.ICollaborationService;
import com.attijarivos.service.IService;
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
@RequestMapping("/collaborations")
@RequiredArgsConstructor
@Slf4j
public class CollaborationController implements ICollaborationController<CollaborationRequest, Long>{

    @Autowired
    @Qualifier("service-layer-collaboration")
    private final ICollaborationService<CollaborationRequest,CollaborationResponse,Long> collaborationService;


    @PostMapping
    @Override
    public ResponseEntity<?> createOne(@RequestBody @Valid CollaborationRequest collaborationRequest) {
        try {
            CollaborationResponse collaborationResponse = collaborationService.createOne(collaborationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(collaborationResponse);
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
            List<CollaborationResponse> collaborationsResponse = collaborationService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(collaborationsResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getOne(@PathVariable("id") Long idCollaboration) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(collaborationService.getOne(idCollaboration));
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> update(@PathVariable("id") Long idCollaboration,@RequestBody @Valid CollaborationUpdateRequest collaborationRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    collaborationService.update(idCollaboration,collaborationRequest)
            );
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RequiredDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long idCollaboration) {
        try {
            collaborationService.delete(idCollaboration);
            return ResponseEntity.status(HttpStatus.OK).body("Bonne Suppression de la collaboration en ligne d'id : "+idCollaboration);
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/join/{id}")
    @Override
    public ResponseEntity<?> joindre(@PathVariable("id") Long idCollaboration,@RequestBody @Valid JoinCollaborationRequest joinCollaborationRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    collaborationService.rejoindre(idCollaboration,joinCollaborationRequest)
            );
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RequiredDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (CollaborationAccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

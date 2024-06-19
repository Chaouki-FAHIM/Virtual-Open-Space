package com.attijarivos.controller;

import com.attijarivos.DTO.request.CollaborationCreateRequest;
import com.attijarivos.DTO.request.CollaborationUpdateRequest;
import com.attijarivos.DTO.request.JoinCollaborationRequest;
import com.attijarivos.DTO.response.CollaborationResponse;
import com.attijarivos.exception.CollaborationAccessDeniedException;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.service.ICollaborationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/collaborations")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class CollaborationController implements ICollaborationController<CollaborationCreateRequest, Long> {

    @Autowired
    @Qualifier("service-layer-collaboration")
    private final ICollaborationService<CollaborationCreateRequest, CollaborationResponse, Long> collaborationService;

    @PostMapping
    @Override
    public ResponseEntity<?> createOne(@RequestBody @Valid CollaborationCreateRequest collaborationCreateRequest) throws RequiredDataException, NotFoundDataException, MicroserviceAccessFailureException {
        try {
            CollaborationResponse collaborationResponse = collaborationService.createOne(collaborationCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(collaborationResponse);
        } catch (RequiredDataException | MicroserviceAccessFailureException | NotFoundDataException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<?> getAll() {
        try {
            Set<CollaborationResponse> collaborationsResponse = collaborationService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(collaborationsResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getOne(@PathVariable("id") Long idCollaboration) throws NotFoundDataException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(collaborationService.getOne(idCollaboration));
        } catch (NotFoundDataException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> update(@PathVariable("id") Long idCollaboration, @RequestBody @Valid CollaborationUpdateRequest collaborationUpdateRequest) throws RequiredDataException, NotFoundDataException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    collaborationService.update(idCollaboration, collaborationUpdateRequest)
            );
        } catch (NotFoundDataException | RequiredDataException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/uninvited-members")
    @Override
    public ResponseEntity<?> getMembersForJoiningCollaboration(@PathVariable("id") Long idCollaboration) throws NotFoundDataException, MicroserviceAccessFailureException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(collaborationService.getMembersForJoiningCollaboration(idCollaboration));
        } catch (NotFoundDataException | MicroserviceAccessFailureException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    @Override
    public ResponseEntity<?> searchCollaboration(@RequestParam("title") String titleCollaboration) throws NotFoundDataException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(collaborationService.searchCollaboration(titleCollaboration));
        } catch (NotFoundDataException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long idCollaboration) throws NotFoundDataException {
        try {
            collaborationService.delete(idCollaboration);
            return ResponseEntity.status(HttpStatus.OK).body("Bonne Suppression de la collaboration en ligne d'id : " + idCollaboration);
        } catch (NotFoundDataException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/join")
    @Override
    public ResponseEntity<?> joindre(@PathVariable("id") Long idCollaboration, @RequestBody @Valid JoinCollaborationRequest joinCollaborationRequest) throws RequiredDataException, NotFoundDataException, CollaborationAccessDeniedException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    collaborationService.joindre(idCollaboration, joinCollaborationRequest)
            );
        } catch (NotFoundDataException | RequiredDataException | CollaborationAccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

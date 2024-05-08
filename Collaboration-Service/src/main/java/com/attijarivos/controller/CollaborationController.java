package com.attijarivos.controller;


import com.attijarivos.DTO.CollaborationRequest;
import com.attijarivos.DTO.CollaborationResponse;
import com.attijarivos.exception.NotFoundDataException;
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

import java.util.List;

@RestController
@RequestMapping("/collaborations")
@RequiredArgsConstructor
@Slf4j
public class CollaborationController implements IController<CollaborationRequest,Long>{

    @Autowired
    @Qualifier("service-layer-collaboration")
    private final IService<CollaborationRequest,CollaborationResponse,Long> collaborationService;


    @PostMapping
    @Override
    public ResponseEntity<?> create(@RequestBody @Valid CollaborationRequest collaborationRequest) {
        try {
            CollaborationResponse collaborationResponse = collaborationService.create(collaborationRequest);
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
    public ResponseEntity<?> update(@PathVariable("id") Long idCollaboration,@RequestBody @Valid CollaborationRequest collaborationRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(collaborationService.update(idCollaboration,collaborationRequest));
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
}

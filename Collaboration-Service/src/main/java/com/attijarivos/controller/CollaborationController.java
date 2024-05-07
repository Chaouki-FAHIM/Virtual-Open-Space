package com.attijarivos.controller;


import com.attijarivos.DTO.CollaborationRequest;
import com.attijarivos.DTO.CollaborationResponse;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.service.IService;
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
    public ResponseEntity<?> create(@RequestBody CollaborationRequest collaborationRequest) {
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
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(collaborationService.getOne(id));
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody CollaborationRequest collaborationRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(collaborationService.update(id,collaborationRequest));
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
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            collaborationService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Bonne Suppression de la collaboration en ligne d'id : "+id);
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

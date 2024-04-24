package com.attijarfivos.collaborationservice.controller;

import com.attijarfivos.collaborationservice.DTO.CollaborationRequest;
import com.attijarfivos.collaborationservice.DTO.CollaborationResponse;
import com.attijarfivos.collaborationservice.exception.RequiredDataException;
import com.attijarfivos.collaborationservice.service.IService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collaborations")
@RequiredArgsConstructor
public class CollaborationController implements IController<CollaborationRequest,Long>{

    @Autowired
    @Qualifier("collaboration")
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
        return null;
    }

    @GetMapping("/id")
    @Override
    public ResponseEntity<?> getOne(Long id) {
        return null;
    }

    @PutMapping("/id")
    @Override
    public ResponseEntity<?> update(Long id, CollaborationRequest collaborationRequest) {
        return null;
    }

    @DeleteMapping("/id")
    @Override
    public ResponseEntity<?> delete(Long id, CollaborationRequest collaborationRequest) {
        return null;
    }
}

package com.attijarivos.membreservice.controller;

import com.attijarivos.membreservice.dto.MembreRequest;
import com.attijarivos.membreservice.dto.MembreResponse;
import com.attijarivos.membreservice.exception.NotFoundDataException;
import com.attijarivos.membreservice.exception.RequiredDataException;
import com.attijarivos.membreservice.service.MembreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/membres")
public class MembreController {

    private final MembreService membreService;


    @PostMapping
    public ResponseEntity<?> createMembre(@RequestBody MembreRequest membreRequest) {
        try {
            MembreResponse membreResponse = membreService.createMembre(membreRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(membreResponse);
        } catch (RequiredDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllMembres() {
        try {
            List<MembreResponse> membresResponse = membreService.getAllMembres();
            return ResponseEntity.ok(membresResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMembre(@PathVariable String id) {
        try {
            MembreResponse membreResponse = membreService.getMembre(id);
            return ResponseEntity.ok(membreResponse);
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

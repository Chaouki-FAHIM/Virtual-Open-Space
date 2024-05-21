package com.attijarivos.controller;

import com.attijarivos.dto.DetailMembreResponse;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.dto.membre.MembreRequest;
import com.attijarivos.dto.membre.MembreResponse;
import com.attijarivos.service.MembreService;
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
            DetailMembreResponse membreResponse = membreService.getOneMembreDetail(id);
            return ResponseEntity.ok(membreResponse);
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

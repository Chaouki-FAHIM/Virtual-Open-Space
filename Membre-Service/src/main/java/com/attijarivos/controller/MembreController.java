package com.attijarivos.controller;

import com.attijarivos.dto.request.MembreUpdateRequest;
import com.attijarivos.dto.response.details.DetailMembreResponse;
import com.attijarivos.exception.MicroserviceAccessFailureException;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RededicationMembreException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.dto.request.MembreRequest;
import com.attijarivos.dto.response.shorts.ShortMembreResponse;
import com.attijarivos.service.MembreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
            ShortMembreResponse shortMembreResponse = membreService.createMembre(membreRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(shortMembreResponse);
        } catch (RequiredDataException | RededicationMembreException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (MicroserviceAccessFailureException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getAllMembres() {
        try {
            List<ShortMembreResponse> membresResponse = membreService.getAllMembres();
            return ResponseEntity.ok(membresResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getMembre(@PathVariable String id) {
        try {
            DetailMembreResponse membreResponse = membreService.getOneMembreDetail(id);
            return ResponseEntity.ok(membreResponse);
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (MicroserviceAccessFailureException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMembres(@RequestParam String fullName) {
        try {
            List<ShortMembreResponse> membresResponse = membreService.searchMembres(fullName);
            return ResponseEntity.ok(membresResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMembre(@PathVariable("id") String idMembre, @RequestBody MembreUpdateRequest request) {
        try {
            DetailMembreResponse membreResponse = membreService.updateMembre(idMembre,request);
            return ResponseEntity.ok(membreResponse);
        } catch (NotFoundDataException | RededicationMembreException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (MicroserviceAccessFailureException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String idMembre) {
        try {
            membreService.deleteMembre(idMembre);
            return ResponseEntity.status(HttpStatus.OK).body("Bonne Suppression de membre d'id : "+idMembre);
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (MicroserviceAccessFailureException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

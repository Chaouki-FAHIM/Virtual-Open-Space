package com.attijarivos.controller;

import com.attijarivos.DTO.MembreDTO;
import com.attijarivos.DTO.TeamMembresRequest;
import com.attijarivos.DTO.TeamRequest;
import com.attijarivos.DTO.TeamResponse;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RededicationMembreException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
@Slf4j
public class TeamController {

    @Autowired
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody @Valid TeamRequest teamRequest) {
        try {
            TeamResponse teamResponse = teamService.createTeam(teamRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(teamResponse);
        } catch (RequiredDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTeams() {
        try {
            List<TeamResponse> teamResponseList = teamService.getAllTeams();
            return ResponseEntity.ok(teamResponseList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable("id") String idTeam) {
        try {
            TeamResponse teamResponse = teamService.getTeamById(idTeam);
            return ResponseEntity.ok(teamResponse);
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/{idTeam}/membres")
    public ResponseEntity<?> addMembresToTeam(@PathVariable String idTeam, @RequestBody @Valid TeamMembresRequest teamMembresRequest) {
        try {
            TeamResponse teamResponse = teamService.addMembresToTeam(idTeam, teamMembresRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(teamResponse);
        } catch (RequiredDataException | RededicationMembreException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable("id") String idTeam) {
        try {
            teamService.deleteTeam(idTeam);
            return ResponseEntity.status(HttpStatus.OK).body("Bonne Suppression d'équipe avec l'identifiant : "+idTeam);
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/syncUpdateMembre")
    public ResponseEntity<?> syncUpdateMemberInTeam(@RequestBody @Valid MembreDTO membreDTO) {
        try {
            Set<TeamResponse> teamResponseList = teamService.syncUpdateMemberInTeam(membreDTO);
            return ResponseEntity.status(HttpStatus.OK).body(teamResponseList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/syncDeleteMembre/{idMembre}")
    public ResponseEntity<?> syncDeleteMemberInTeam(@PathVariable("idMembre") String membreDTO) {
        try {
            Set<TeamResponse> teamResponseList = teamService.syncDeleteMemberInTeam(membreDTO);
            return ResponseEntity.status(HttpStatus.OK).body(teamResponseList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

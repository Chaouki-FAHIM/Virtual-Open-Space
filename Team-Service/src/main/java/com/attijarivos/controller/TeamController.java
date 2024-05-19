package com.attijarivos.controller;

import com.attijarivos.DTO.TeamMembresRequest;
import com.attijarivos.DTO.TeamRequest;
import com.attijarivos.DTO.TeamResponse;
import com.attijarivos.exception.NotFoundDataException;
import com.attijarivos.exception.RequiredDataException;
import com.attijarivos.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
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
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/{idTeam}/membres")
    public ResponseEntity<?> addMembresToTeam(@PathVariable String idTeam, @RequestBody @Valid TeamMembresRequest teamMembresRequest) {
        try {
            TeamResponse teamResponse = teamService.addMembresToTeam(idTeam, teamMembresRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(teamResponse);
        } catch (RequiredDataException | NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

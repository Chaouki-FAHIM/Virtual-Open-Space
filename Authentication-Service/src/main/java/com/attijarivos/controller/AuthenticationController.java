package com.attijarivos.controller;

import com.attijarivos.dto.request.AccountRequest;
import com.attijarivos.dto.request.LoginRequest;
import com.attijarivos.exception.RededicationAccountException;
import com.attijarivos.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok().body(authenticationService.login(loginRequest));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountRequest accountRequest) {
        try {
            return ResponseEntity.ok()
                    .body(authenticationService.register(accountRequest));
        } catch (RededicationAccountException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}

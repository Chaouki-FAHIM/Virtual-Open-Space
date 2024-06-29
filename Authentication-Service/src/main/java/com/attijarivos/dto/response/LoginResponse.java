package com.attijarivos.dto.response;

import lombok.*;

import java.util.Set;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @ToString @Builder
public class LoginResponse {
    private int accountId;
    private String username;
    private String password;
    private Set<String> roles;
    private String token;
}

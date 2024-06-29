package com.attijarivos.dto.request;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @ToString @Builder
public class LoginRequest {
    private String username;
    private String password;
}

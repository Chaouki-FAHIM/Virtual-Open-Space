package com.attijarivos.dto.request;

import com.attijarivos.model.Role;
import lombok.*;

import java.util.Set;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @ToString @Builder
public class AccountRequest {
    private String username;
    private String password;
    private Set<Role> roles;
}

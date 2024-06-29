package com.attijarivos.dto.response;

import com.attijarivos.model.Role;
import lombok.*;

import java.util.Set;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @ToString @Builder
public class AccountResponse {
    private int accountId;
    private String username;
    private String password;
    private Set<Role> roles;
}

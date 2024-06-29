package com.attijarivos.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity @Table(name = "account")
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @Builder
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "account_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private Set<Role> roles;
}

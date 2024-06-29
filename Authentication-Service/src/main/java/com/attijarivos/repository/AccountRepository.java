package com.attijarivos.repository;

import com.attijarivos.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Set<Account> findByUsernameIgnoreCase(String username);
}

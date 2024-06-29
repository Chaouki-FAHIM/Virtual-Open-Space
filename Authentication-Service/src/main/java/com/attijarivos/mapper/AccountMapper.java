package com.attijarivos.mapper;

import com.attijarivos.dto.request.AccountRequest;
import com.attijarivos.dto.response.AccountResponse;
import com.attijarivos.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account reqToModel(AccountRequest request) {
        return Account.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .roles(request.getRoles())
                .build();
    }

    public AccountResponse modelToRes(Account account) {
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRoles())
                .build();
    }
}

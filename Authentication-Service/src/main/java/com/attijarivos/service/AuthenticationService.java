package com.attijarivos.service;

import com.attijarivos.dto.request.AccountRequest;
import com.attijarivos.dto.request.LoginRequest;
import com.attijarivos.dto.response.AccountResponse;
import com.attijarivos.dto.response.LoginResponse;
import com.attijarivos.exception.RededicationAccountException;
import com.attijarivos.mapper.AccountMapper;
import com.attijarivos.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AccountMapper accountMapper;
    @Autowired
    private AccountRepository accountRepository;

//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private JwtEncoder jwtEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        log.debug("Start authenticating by {}", loginRequest);

//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//        );
//
//        log.debug("End authentication successfully : {}", authentication);
//
//        log.debug("Start creating Token for user {}", loginRequest.getUsername());
//
//        Instant instant = Instant.now();
//        Set<String> roles = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .map(role -> "ROLE_" + role)
//                .collect(Collectors.toSet());
//
//        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
//                .issuedAt(instant)
//                .expiresAt(instant.plus(5, ChronoUnit.MINUTES))
//                .subject(loginRequest.getUsername())
//                .claim("scope", String.join(" ", roles))
//                .build();
//
//        JwtEncoderParameters jwtEncoderParameters =
//                JwtEncoderParameters.from(
//                        JwsHeader.with(MacAlgorithm.HS512).build(),
//                        jwtClaimsSet
//                );
//        log.info("End creating Token for user {}", loginRequest.getUsername());
//
//        return LoginResponse.builder()
//                .username(loginRequest.getUsername())
//                .password(loginRequest.getPassword())
//                .roles(roles)
//                .token(jwtEncoder.encode(jwtEncoderParameters).getTokenValue())
//                .build();
        return null;
    }

    public AccountResponse register(AccountRequest accountRequest) throws RededicationAccountException {
        log.debug("Start registering account {}", accountRequest);
//        Set<Account> accounts = accountRepository.findByUsernameIgnoreCase(accountRequest.getUsername());
//
//        if (!accounts.isEmpty()) {
//            log.error("Account Data {} already consumed", accountRequest);
//            throw new RededicationAccountException();
//        }
//
//        // Encode the password before saving
//        String encodedPassword = passwordEncoder.encode(accountRequest.getPassword());
//        accountRequest.setPassword(encodedPassword);
//
//        log.debug("Valid Data for registering a new account {}", accountRequest);
//
//        Account account = accountRepository.save(accountMapper.reqToModel(accountRequest));
//
//        log.debug("End registering account {}", account);
//
//        return accountMapper.modelToRes(account);
        return null;
    }
}
